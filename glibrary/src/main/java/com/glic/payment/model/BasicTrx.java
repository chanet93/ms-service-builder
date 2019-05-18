package com.glic.payment.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.glic.payment.constants.ECardType;
import com.glic.payment.constants.EResponseCode;
import com.glic.payment.constants.ESettlementIndicator;
import com.glic.payment.constants.ESettlementLevel;
import com.glic.payment.constants.ESettlementStatus;
import com.glic.payment.constants.ETimeZone;
import com.glic.payment.constants.ETrxStatus;
import com.glic.payment.constants.ETrxType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BasicTrx {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @EqualsAndHashCode.Include
   private Long trxId;

   private Long originalTrxId;

   private Long followOnId;

   private String uniqueId;

   //Merchant Information
   private Long merchantId;

   private String merchantName;

   private Long branchId;

   private String branchName;

   private String branchAddress;

   @Enumerated(EnumType.STRING)
   private ETimeZone branchTimeZone;

   private Long departmentId;

   private String departmentName;

   private String departmentMCC;

   private Long terminalId;

   private String terminalName;

   private String terminalSerialNumber;

   private String terminalType;

   private String acquirerTerminalId;

   private String acquirerMerchantId;

   //Card fields
   private String pan;

   private Integer panSequence;

   private String maskedPan;

   private String cardToken;

   private String cardTokenOneWay;

   @GlicLocalDate
   private LocalDate cardTokenExpDateTime;

   private String tokenEntityId;

   @GlicLocalDate
   private LocalDate cardExpDate;

   private String track2;

   private String maskedTrack2;

   private String track1;

   private String cardHolderName;

   private String chPersonalId;

   private String emvDataRequest;

   private String emvDataResponse;

   private String cvv2;

   private String cvv2ResponseCode;

   private String pinBlock;

   private String cardScheme;

   @Enumerated(EnumType.STRING)
   private ECardType cardType;

   //Amount fields
   @NotNull
   private BigDecimal totalAmount;

   private BigDecimal authAmount = new BigDecimal(0);

   private BigDecimal taxAmount = new BigDecimal(0);

   private BigDecimal discountAmount = new BigDecimal(0);

   private BigDecimal tipAmount = new BigDecimal(0);

   private BigDecimal installmentFirstAmount = new BigDecimal(0);

   private BigDecimal installmentNoFirstAmount = new BigDecimal(0);

   private Integer installmentCount = 0;

   private Integer installmentDeferral = 0;

   @NotNull
   private Integer currency;

   //Acquirer Response Fields
   private String acqResponseCode;

   private String acqResponseText;

   private String acqAuthCoded;

   private String acqStan;

   private String acqRrn;

   //GW Response Status
   @Enumerated(EnumType.STRING)
   private EResponseCode gatewayResponseCode;

   private String gatewayResponseText;

   @Enumerated(EnumType.STRING)
   private ETrxStatus trxStatus;

   //Customer fields
   private String customerName;

   private String customerEmail;

   private String customerAddress;

   private String customerAddress2;

   private String customerPhone;

   private String customerCountry;

   private String shippingName;

   private String shippingEmail;

   private String shippingAddress;

   private String shippingAddress2;

   private String shippingPhone;

   private String shippingCity;

   private String shippingCountry;

   private String shippingState;

   private String shippingZipCode;

   private String billingName;

   private String billingEmail;

   private String billingAddress;

   private String billingAddress2;

   private String billingPhone;

   private String billingCity;

   private String billingCountry;

   private String billingState;

   private String billingZipCode;

   //Transmition Data
   private String sourceIp;

   private String sourceName;

   private String sourceChannel;

   private String protocolVersion = "2.1";

   private String softwareVersion;

   //Extra data
   private String extraData1;

   private String extraData2;

   private String extraData3;

   private String extraData4;

   private String extraData5;

   private String extraData6;

   private String extraData7;

   private String extraData8;

   private String extraData9;

   private String extraData10;

   private String extraDataResponse1;

   private String extraDataResponse2;

   private String extraDataResponse3;

   private String extraDataResponse4;

   private String extraDataResponse5;

   private String extraDataResponse6;

   private String extraDataResponse7;

   private String extraDataResponse8;

   private String extraDataResponse9;

   private String extraDataResponse10;

   private String extraJsonData1;

   private String extraJsonData2;

   private String extraJsonData3;

   private String extraJsonData4;

   private String extraJsonData5;

   private String extraJsonData6;

   private String extraJsonData7;

   private String extraJsonData8;

   private String extraJsonData9;

   private String extraJsonData10;

   private String extraJsonDataResponse1;

   private String extraJsonDataResponse2;

   private String extraJsonDataResponse3;

   private String extraJsonDataResponse4;

   private String extraJsonDataResponse5;

   private String extraJsonDataResponse6;

   private String extraJsonDataResponse7;

   private String extraJsonDataResponse8;

   private String extraJsonDataResponse9;

   private String extraJsonDataResponse10;

   //Misc Data
   @Enumerated(EnumType.STRING)
   @NotNull
   private ETrxType trxType;

   private String acquirerId;

   private Boolean online = true;

   private String routerAcquirerId;

   private Boolean replicate = true;

   //Settlement data
   @Enumerated(EnumType.STRING)
   private ESettlementIndicator settlementIndicator;

   @Enumerated(EnumType.STRING)
   private ESettlementStatus settlementStatus;

   @Enumerated(EnumType.STRING)
   private ESettlementLevel settlementLevel;

   private Integer batchId;

   private String invoceNumber;

   private String settExtraDataRequest;

   //Date time fields
   @GlicLocalDateTime
   private LocalDateTime dtRequest;

   @GlicLocalDateTime
   private LocalDateTime dtResponse;

   @GlicLocalDateTime
   private LocalDateTime dtAuthorization;

   @GlicLocalDateTime
   private LocalDateTime dtSettlement;

   @GlicLocalDateTime
   private LocalDateTime dtRequestBranchTz;

   @GlicLocalDateTime
   private LocalDateTime dtResponseBranchTz;

   @GlicLocalDateTime
   private LocalDateTime dtAuthorizationBranchTz;

   @GlicLocalDateTime
   private LocalDateTime dtSettlementBranchTz;

   //Payment Page attributes
   private String successCallbackUrl;

   private String cancelCallbackUrl;

   private String rejectCallbackUrl;

   private String errorCallbackUrl;

   //Related Trx data
   @Transient
   @JsonSerialize
   private BasicTrx relatedTrxData;

}
