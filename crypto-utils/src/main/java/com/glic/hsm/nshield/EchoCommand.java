package com.glic.hsm.nshield;

import com.glic.hsm.commons.Command;
import com.glic.hsm.nshield.request.EchoReq;
import com.glic.hsm.nshield.response.EchoResp;
import com.glic.hsm.payshield.ErrorsDef;


public class EchoCommand extends AbstractCommand implements Command<EchoReq, EchoResp> {

   @Override
   public EchoResp execute(EchoReq req) {
      EchoResp resp = new EchoResp();
      resp.setErrorCode(ErrorsDef.NO_ERROR);
      resp.setMessageData(req.getMessageData());
      return resp;
   }

}
