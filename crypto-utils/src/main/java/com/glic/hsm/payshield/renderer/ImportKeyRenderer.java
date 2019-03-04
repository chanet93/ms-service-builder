package com.glic.hsm.payshield.renderer;

import java.nio.ByteBuffer;

import com.glic.hsm.payshield.exception.HsmException;
import com.glic.hsm.payshield.request.ImportKeyReq;

/**
 * Created by erwine1 on 27/04/17.
 */
public class ImportKeyRenderer implements RequestRenderer<ImportKeyReq> {

   private final String KEYSCHEME = "U";
   private final String ATALLAVARIANT = "00";

   @Override
   public ByteBuffer renderCommandRequest(ImportKeyReq req) throws HsmException {

      int size =
            req.getCommandCode().length() // 2 bytes ('A6')
            + 3 // Key type BDK,LMK etc
            + req.getZmk().length() // ZMK
            + req.getEncryptedKey().length() // HMAC key format
            + 1 //key scheme
            + 2 //atalla variant
            + (req.getLmkIdentifier() != null ? 3 : 0); // '%' + LMK identifier

      ByteBuffer command = ByteBuffer.allocate(size);
      command.put(req.getCommandCode().getBytes(ASCII));
      command.put(req.getKeyTypeEnum().getCommandValue().getBytes(ASCII));
      command.put(req.getZmk().getBytes(ASCII));
      command.put(req.getEncryptedKey().getBytes(ASCII));
      command.put(KEYSCHEME.getBytes(ASCII));
      command.put(ATALLAVARIANT.getBytes(ASCII));
      return command;
   }

}