package com.glic.hsm.nshield;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.glic.hsm.commons.Command;
import com.glic.hsm.commons.CommandChainingItem;
import com.glic.hsm.commons.CommandReq;
import com.glic.hsm.commons.CommandResp;
import com.glic.hsm.nshield.request.CommandChainingReq;
import com.glic.hsm.nshield.response.CommandChainingResp;
import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.exception.HsmException;

/**
 * @author erwine1
 * This class process a set of commands in chain mode.
 */
public class CommandChainingCommand extends AbstractCommand implements Command<CommandChainingReq, CommandChainingResp> {

   private static final Logger LOGGER = Logger.getLogger(CommandChainingCommand.class.getName());

   @Override
   public CommandChainingResp execute(CommandChainingReq req) throws HsmException {

      CommandChainingResp resp = new CommandChainingResp();

      LOGGER.log(Level.FINE, "Executing {0} commands", req.getCommands().size());

      for (int i = 0; i < req.getCommands().size(); i++) {
         CommandChainingItem<CommandReq, CommandResp> commandChainingItem = (CommandChainingItem<CommandReq, CommandResp>) req.getCommands().get(i);

         Command command = commandChainingItem.getCommand();
         CommandReq commandReq = commandChainingItem.getRequest();
         CommandResp commandResp = (CommandResp) command.execute(commandReq);
         commandResp.setHeader(commandChainingItem.getRequest().getHeader());
         resp.getCommandResponses().add(commandResp);
      }

      resp.setErrorCode(ErrorsDef.NO_ERROR);
      for (CommandResp commandResp : resp.getCommandResponses()) {
         if (!commandResp.getErrorCode().equals(ErrorsDef.NO_ERROR)) {
            resp.setErrorCode(ErrorsDef.UNKNOWN_ERROR);
            break;
         }
      }

      return resp;
   }
}
