package com.glic.hsm.payshield.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class NetworkInformationResp extends HsmCommandResp {

	private List<NetworkInformationRespRecord> records = new ArrayList<NetworkInformationRespRecord>();

	private String totalBytesSent;
	private String totalByesReceived;
	private String totalUnicastPacketsSent;
	private String totalUnicastPacketsReceived;
	private String totalNonUnicastPacketsSent;
	private String totalNonUnicastPacketsReceived;
	private String totalPacketDiscardedDuringSend;
	private String totalPacketDiscardedDuringReceive;
	private String totalErrorsDuringSent;
	private String totalErrorsDuringReceive;
	private String totalUnknowPackets;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getTotalBytesSent() {
		return totalBytesSent;
	}

	public String getTotalByesReceived() {
		return totalByesReceived;
	}

	public String getTotalUnicastPacketsSent() {
		return totalUnicastPacketsSent;
	}

	public String getTotalUnicastPacketsReceived() {
		return totalUnicastPacketsReceived;
	}

	public String getTotalNonUnicastPacketsSent() {
		return totalNonUnicastPacketsSent;
	}

	public String getTotalNonUnicastPacketsReceived() {
		return totalNonUnicastPacketsReceived;
	}

	public String getTotalPacketDiscardedDuringSend() {
		return totalPacketDiscardedDuringSend;
	}

	public String getTotalPacketDiscardedDuringReceive() {
		return totalPacketDiscardedDuringReceive;
	}

	public String getTotalErrorsDuringSent() {
		return totalErrorsDuringSent;
	}

	public String getTotalErrorsDuringReceive() {
		return totalErrorsDuringReceive;
	}

	public String getTotalUnknowPackets() {
		return totalUnknowPackets;
	}

	public void setTotalBytesSent(String totalBytesSent) {
		this.totalBytesSent = totalBytesSent;
	}

	public void setTotalByesReceived(String totalByesReceived) {
		this.totalByesReceived = totalByesReceived;
	}

	public void setTotalUnicastPacketsSent(String totalUnicastPacketsSent) {
		this.totalUnicastPacketsSent = totalUnicastPacketsSent;
	}

	public void setTotalUnicastPacketsReceived(
			String totalUnicastPacketsReceived) {
		this.totalUnicastPacketsReceived = totalUnicastPacketsReceived;
	}

	public void setTotalNonUnicastPacketsSent(String totalNonUnicastPacketsSent) {
		this.totalNonUnicastPacketsSent = totalNonUnicastPacketsSent;
	}

	public void setTotalNonUnicastPacketsReceived(
			String totalNonUnicastPacketsReceived) {
		this.totalNonUnicastPacketsReceived = totalNonUnicastPacketsReceived;
	}

	public void setTotalPacketDiscardedDuringSend(
			String totalPacketDiscardedDuringSend) {
		this.totalPacketDiscardedDuringSend = totalPacketDiscardedDuringSend;
	}

	public void setTotalPacketDiscardedDuringReceive(
			String totalPacketDiscardedDuringReceive) {
		this.totalPacketDiscardedDuringReceive = totalPacketDiscardedDuringReceive;
	}

	public void setTotalErrorsDuringSent(String totalErrorsDuringSent) {
		this.totalErrorsDuringSent = totalErrorsDuringSent;
	}

	public void setTotalErrorsDuringReceive(String totalErrorsDuringReceive) {
		this.totalErrorsDuringReceive = totalErrorsDuringReceive;
	}

	public void setTotalUnknowPackets(String totalUnknowPackets) {
		this.totalUnknowPackets = totalUnknowPackets;
	}

	public NetworkInformationResp() {
		setCommandCode(NETWORK_INFORMATION);
	}

	public List<NetworkInformationRespRecord> getRecords() {
		return records;
	}

	public void setRecords(List<NetworkInformationRespRecord> records) {
		this.records = records;
	}

	public void setRecord(NetworkInformationResp.NetworkInformationRespRecord record) {
		getRecords().add(record);
	}

public class NetworkInformationRespRecord {

		private String protocol;
		private String localPort;
		private String remoteAddress;
		private String remotePort;
		private String state;
		private String duration;

		public String getProtocol() {
			return protocol;
		}

		public String getLocalPort() {
			return localPort;
		}

		public String getRemoteAddress() {
			return remoteAddress;
		}

		public String getRemotePort() {
			return remotePort;
		}

		public String getState() {
			return state;
		}

		public String getDuration() {
			return duration;
		}

		public void setProtocol(String protocol) {
			this.protocol = protocol;
		}

		public void setLocalPort(String localPort) {
			this.localPort = localPort;
		}

		public void setRemoteAddress(String remoteAddress) {
			this.remoteAddress = remoteAddress;
		}

		public void setRemotePort(String remotePort) {
			this.remotePort = remotePort;
		}

		public void setState(String state) {
			this.state = state;
		}

		public void setDuration(String duration) {
			this.duration = duration;
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}

	}

}

