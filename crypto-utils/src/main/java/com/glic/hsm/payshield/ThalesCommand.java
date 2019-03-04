package com.glic.hsm.payshield;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.glic.hsm.commons.Command;
import com.glic.hsm.payshield.connector.HsmConnector;
import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.exception.HsmUnsupportedCommandException;
import com.glic.hsm.payshield.exception.InvalidHashAlgorithmException;
import com.glic.hsm.payshield.parser.ResponseParser;
import com.glic.hsm.payshield.renderer.RequestRenderer;
import com.glic.hsm.payshield.request.PayshieldCommandReq;
import com.glic.hsm.payshield.response.HsmCommandResp;

public class ThalesCommand<REQ extends PayshieldCommandReq, RESP extends HsmCommandResp> implements Command<REQ, RESP> {

   private static final Logger LOGGER = Logger.getLogger(ThalesCommand.class.getName());

   private RequestRenderer<REQ> requestRenderer;

   private ResponseParser<REQ, RESP> responseParser;

   private HsmConnector hsmConnector;

   @Override
   public RESP execute(REQ request) throws HsmException {

      LOGGER.log(Level.FINE, "Executing command [{0}]", request.getCommandCode());

      try {
         ByteBuffer requestPayload = requestRenderer.renderCommandRequest(request);

         ByteBuffer responsePayload = null;
         int retries = 1;
         int maxRetries = hsmConnector.getMaxRetries();
         do {

            try {
               responsePayload = hsmConnector.send(requestPayload);
            } catch (HsmException e) {
               LOGGER.log(Level.WARNING, "Response Payload not received , retrying {0}", retries);
               if (retries == maxRetries) {
                  throw e;
               }
            }
            retries++;

         } while (responsePayload == null && retries <= maxRetries);

         if (responsePayload != null) {
            // set request message in case the parser needs to query request fields
            responseParser.setRequestMessage(request);

            // parse command response
            RESP response = responseParser.parseCommandResponse(responsePayload);

            return response;
         } else {
            return null;
         }
      } catch (InvalidHashAlgorithmException e) {
         LOGGER.log(Level.WARNING, "Exception caught executing command [" + request.getCommandCode() + "]", e);
         responseParser.createErrorResponse(ErrorsDef.INVALID_HASH_IDENTIFIER);
         throw new HsmException(e);
      } catch (HsmUnsupportedCommandException e) {
         LOGGER.log(Level.WARNING, "HSM exception unsupported command [{0}]", request.getCommandCode());
         responseParser.createErrorResponse(ErrorsDef.UNKNOWN_ERROR);
         throw e;
      } catch (HsmException e) {
         LOGGER.log(Level.WARNING, "HSM exception caught executing command [{0}]", request.getCommandCode());
         responseParser.createErrorResponse(ErrorsDef.UNKNOWN_ERROR);
         throw new HsmException(e);
      } catch (Exception e) {
         LOGGER.log(Level.WARNING, "Unexpected exception caught executing command [{0}]", request.getCommandCode());
         responseParser.createErrorResponse(ErrorsDef.UNKNOWN_ERROR);
         throw new HsmException(e);
      }
   }

   public RequestRenderer<REQ> getRequestRenderer() {
      return requestRenderer;
   }

   public void setRequestRenderer(RequestRenderer<REQ> requestRenderer) {
      this.requestRenderer = requestRenderer;
   }

   public ResponseParser<REQ, RESP> getResponseParser() {
      return responseParser;
   }

   public void setResponseParser(ResponseParser<REQ, RESP> responseParser) {
      this.responseParser = responseParser;
   }

   public HsmConnector getHsmConnector() {
      return hsmConnector;
   }

   public void setHsmConnector(HsmConnector hsmConnector) {
      this.hsmConnector = hsmConnector;
   }

}
