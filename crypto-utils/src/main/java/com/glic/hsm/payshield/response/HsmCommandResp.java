/*
 * Copyright (c) 2013. VeriFone Uruguay S.A. All Rights Reserved.
 * 
 * This Module contains Propietary Information of
 * VeriFone Uruguay S.A. and should be treated as Confidential.
 * 
 * This Module is provided "AS IS" Without any warranties implicit
 * or explicit.
 * 
 * The use of this Module implies the acceptance of all the terms
 * and conditions of the "User License".
 */
package com.glic.hsm.payshield.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glic.hsm.commons.CommandResp;

/**
 * Response of a PayshieldCommandReq
 *
 * @date 23/9/2014
 * @version $Id$
 * @author ErnestoQ1
 */
public class HsmCommandResp extends CommandResp
{
    public static final String ENCRYPT_RESP = "M1";

    public static final String DECRYPT_RESP = "M3";
    
    public static final String TRANSLATE_DATA_BLOCK_RESP = "M5";

    public static final String ENCRYPT_RSA_RESP = "??";

    public static final String DECRYPT_RSA_RESP = "GI";

    public static final String HASH_RESP = "GN";

    public static final String IMPORT_KEY_RESP = "A7";

    public static final String PIN_RE_ENCRYPT_RESP = "G1";

    public static final String ECHO_RESP = "B3";

    public static final String HSM_STATUS_RESP = "NP";
    
    public static final String RANDOM_VALUE_RESP = "N1";
    
    public static final String PERFORM_DIAGNOSTICS_RESP = "ND";
    
    public static final String GENERATE_KEY_RESP = "A1";
    
    public static final String COMMAND_CHAINING_RESP = "NL";
    
    public static final String GENERATE_HMAC_KEY_RESP = "L1";
    
    public static final String GENERATE_HMAC_RESP = "LR";
    
    public static final String VALIDATE_HMAC_RESP = "LT";
    
    public static final String NETWORK_INFORMATION = "NI";
    

    // Hsm Response Properties
    private String commandCode;
    
    public HsmCommandResp() {
    }

	public String getCommandCode() {
		return commandCode;
	}

	protected void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


}