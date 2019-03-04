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
package com.glic.hsm.payshield.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glic.hsm.commons.CommandReq;

/**
 * Represent an abstract HSM command, a specific command Should inherit from this class. This class handles the
 * connection with the HSM Thales.
 * 
 * @author ErnestoQ1
 */
public class PayshieldCommandReq extends CommandReq {

	// Constants, commands codes etc
	public static final String ENCRYPT_CMD = "M0";

	public static final String DECRYPT_CMD = "M2";

	public static final String TRANSLATE_DATA_BLOCK_CMD = "M4";

	public static final String ENCRYPT_RSA_CMD = "??";

	public static final String DECRYPT_RSA_CMD = "GI";

	public static final String HASH_CMD = "GM";

	public static final String IMPORT_KEY_CMD = "A6";

	public static final String PIN_RE_ENCRYPT_CMD = "G0";

	public static final String ECHO_CMD = "B2";

	public static final String HSM_STATUS_CMD = "NO";

	public static final String RANDOM_VALUE_CMD = "N0";

	public static final String PERFORM_DIAGNOSTICS_CMD = "NC";

	public static final String GENERATE_KEY_CMD = "A0";

	public static final String COMMAND_CHAINING_CMD = "NK";

	public static final String GENERATE_HMAC_KEY_CMD = "L0";

	public static final String GENERATE_HMAC_CMD = "LQ";

	public static final String VALIDATE_HMAC_CMD = "LS";
	
	public static final String NETWORK_INFORMATION = "NI";
	
	private Integer lmkIdentifier;

	private String commandCode;
	
	public String getCommandCode() {
		return commandCode;
	}

	protected void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public Integer getLmkIdentifier() {
		return lmkIdentifier;
	}

	/**
	 * @param lmkIdentifier Optional LMK identifier; min value = '00'; max value is defined by license.
	 */
	public void setLmkIdentifier(Integer lmkIdentifier) {
		this.lmkIdentifier = lmkIdentifier;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}