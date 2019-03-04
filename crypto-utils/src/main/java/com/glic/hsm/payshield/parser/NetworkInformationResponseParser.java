package com.glic.hsm.payshield.parser;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

import com.glic.hsm.payshield.ErrorsDef;
import com.glic.hsm.payshield.parser.utils.ParseUtils;
import com.glic.hsm.payshield.request.NetworkInformationReq;
import com.glic.hsm.payshield.response.NetworkInformationResp;

public class NetworkInformationResponseParser extends AbstractBaseResponseParser<NetworkInformationReq, NetworkInformationResp> {

	@Override
	public NetworkInformationResp parseCommandResponse(ByteBuffer responseByteBuffer) {

		String errorCode = ParseUtils.getNextString(responseByteBuffer,COMMAND_CODE_LENGTH,ASCII);
    	
    	NetworkInformationResp resp = new NetworkInformationResp();
    	resp.setErrorCode(errorCode);
    	if (ErrorsDef.NO_ERROR.equals(errorCode)) {
    		 String listSize_aux = ParseUtils.getNextString(responseByteBuffer,4,ASCII);
    		 
    		 int listSize =  Integer.valueOf(listSize_aux);
    		 
    		 for (int i = 0; i < listSize; i++) {
    			 NetworkInformationResp.NetworkInformationRespRecord record = resp.new NetworkInformationRespRecord();
    			 
    			 String protocol = ParseUtils.getNextString(responseByteBuffer,1,ASCII);
    			 if("0".equals(protocol)){
    				 record.setProtocol("TCP");
    			 }else{
    				 record.setProtocol("UDP");
    			 }
    			 
    				 String localPortAux = ParseUtils.getNextString(responseByteBuffer, 4, ASCII);
    				 int localPort = Integer.parseInt(localPortAux, 16);    			
    				 record.setLocalPort(String.valueOf(localPort));
    			
    				 String remoteAddressAux = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
    				InetAddress remoteAddress = null;
					try {
						remoteAddress = InetAddress.getByAddress(DatatypeConverter.parseHexBinary(remoteAddressAux));
					} catch (UnknownHostException e1) {
					}
					record.setRemoteAddress(remoteAddress.getHostAddress());
    			 
					String remotePortAux = ParseUtils.getNextString(responseByteBuffer, 4, ASCII);
					int remotePort = Integer.parseInt(remotePortAux, 16);    			
					record.setRemotePort(String.valueOf(remotePort));
    			 
    			 
	    			 if("0".equals(protocol)){
	    				 String state = ParseUtils.getNextString(responseByteBuffer,1,ASCII);
	        			 if("0".equals(state)){
	        				 record.setState("ESTABLISHED");
	        			 }else {
	        				 record.setState("DISCONETED");
	        			 }
	    			 }
    			 
	    			 String durationAux = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
    				 int duration = Integer.parseInt(durationAux, 16);    			
    				 record.setDuration(String.valueOf(duration));

    			 resp.setRecord(record);
    			 
    		 }
    		 
    		 if(responseByteBuffer.capacity() !=  responseByteBuffer.position()){
    			 String tbs_ = ParseUtils.getNextString(responseByteBuffer, 16, ASCII);
        		 int tbs = Integer.parseInt(tbs_, 16); 
        		 resp.setTotalBytesSent(String.valueOf(tbs));
        		 
        		 String tbr_ = ParseUtils.getNextString(responseByteBuffer, 16, ASCII);
        		 int tbr = Integer.parseInt(tbr_, 16); 
        		 resp.setTotalByesReceived(String.valueOf(tbr));
        		 
        		 String ups_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int ups = Integer.parseInt(ups_, 16); 
        		 resp.setTotalUnicastPacketsSent(String.valueOf(ups));
        		 
        		 String upr_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int upr = Integer.parseInt(upr_, 16); 
        		 resp.setTotalUnicastPacketsReceived(String.valueOf(upr));
        		 
        		 String nups_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int nups = Integer.parseInt(nups_, 16); 
        		 resp.setTotalNonUnicastPacketsSent(String.valueOf(nups));
        		 
        		 String nupr_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int nupr = Integer.parseInt(nupr_, 16); 
        		 resp.setTotalNonUnicastPacketsReceived(String.valueOf(nupr));
        		 
        		 String pdds_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int pdds = Integer.parseInt(pdds_, 16); 
        		 resp.setTotalPacketDiscardedDuringSend(String.valueOf(pdds));
        		 
        		 String pddr_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int pddr = Integer.parseInt(pddr_, 16); 
        		 resp.setTotalPacketDiscardedDuringReceive(String.valueOf(pddr));
        		 
        		 String eds_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int eds = Integer.parseInt(eds_, 16); 
        		 resp.setTotalErrorsDuringSent(String.valueOf(eds));
        		 
        		 String edr_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int edr = Integer.parseInt(edr_, 16); 
        		 resp.setTotalErrorsDuringReceive(String.valueOf(edr));
        		 
        		 String up_ = ParseUtils.getNextString(responseByteBuffer, 8, ASCII);
        		 int up = Integer.parseInt(up_, 16); 
        		 resp.setTotalUnknowPackets(String.valueOf(up));
    			 
    		 }
    		 
    	}
    	
        return resp;
	}
}
