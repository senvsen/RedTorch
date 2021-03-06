package xyz.redtorch.gateway.ctp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xyz.redtorch.api.jctp.td.CThostFtdcAccountregisterField;
import xyz.redtorch.api.jctp.td.CThostFtdcBatchOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcBrokerTradingAlgosField;
import xyz.redtorch.api.jctp.td.CThostFtdcBrokerTradingParamsField;
import xyz.redtorch.api.jctp.td.CThostFtdcBulletinField;
import xyz.redtorch.api.jctp.td.CThostFtdcCFMMCTradingAccountKeyField;
import xyz.redtorch.api.jctp.td.CThostFtdcCFMMCTradingAccountTokenField;
import xyz.redtorch.api.jctp.td.CThostFtdcCancelAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcChangeAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcCombActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcCombInstrumentGuardField;
import xyz.redtorch.api.jctp.td.CThostFtdcContractBankField;
import xyz.redtorch.api.jctp.td.CThostFtdcDepthMarketDataField;
import xyz.redtorch.api.jctp.td.CThostFtdcEWarrantOffsetField;
import xyz.redtorch.api.jctp.td.CThostFtdcErrorConditionalOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcExchangeField;
import xyz.redtorch.api.jctp.td.CThostFtdcExchangeMarginRateAdjustField;
import xyz.redtorch.api.jctp.td.CThostFtdcExchangeMarginRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcExchangeRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcExecOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcExecOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcForQuoteField;
import xyz.redtorch.api.jctp.td.CThostFtdcForQuoteRspField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputBatchOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputCombActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputExecOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputExecOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputForQuoteField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputOptionSelfCloseActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputOptionSelfCloseField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputQuoteActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInputQuoteField;
import xyz.redtorch.api.jctp.td.CThostFtdcInstrumentCommissionRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcInstrumentField;
import xyz.redtorch.api.jctp.td.CThostFtdcInstrumentMarginRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcInstrumentOrderCommRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcInstrumentStatusField;
import xyz.redtorch.api.jctp.td.CThostFtdcInvestUnitField;
import xyz.redtorch.api.jctp.td.CThostFtdcInvestorField;
import xyz.redtorch.api.jctp.td.CThostFtdcInvestorPositionCombineDetailField;
import xyz.redtorch.api.jctp.td.CThostFtdcInvestorPositionDetailField;
import xyz.redtorch.api.jctp.td.CThostFtdcInvestorPositionField;
import xyz.redtorch.api.jctp.td.CThostFtdcInvestorProductGroupMarginField;
import xyz.redtorch.api.jctp.td.CThostFtdcMMInstrumentCommissionRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcMMOptionInstrCommRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcNoticeField;
import xyz.redtorch.api.jctp.td.CThostFtdcNotifyQueryAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcOpenAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcOptionInstrCommRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcOptionInstrTradeCostField;
import xyz.redtorch.api.jctp.td.CThostFtdcOptionSelfCloseActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcOptionSelfCloseField;
import xyz.redtorch.api.jctp.td.CThostFtdcOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcParkedOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcParkedOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcProductExchRateField;
import xyz.redtorch.api.jctp.td.CThostFtdcProductField;
import xyz.redtorch.api.jctp.td.CThostFtdcProductGroupField;
import xyz.redtorch.api.jctp.td.CThostFtdcQryInstrumentField;
import xyz.redtorch.api.jctp.td.CThostFtdcQryInvestorPositionField;
import xyz.redtorch.api.jctp.td.CThostFtdcQryTradingAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcQueryCFMMCTradingAccountTokenField;
import xyz.redtorch.api.jctp.td.CThostFtdcQueryMaxOrderVolumeField;
import xyz.redtorch.api.jctp.td.CThostFtdcQuoteActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcQuoteField;
import xyz.redtorch.api.jctp.td.CThostFtdcRemoveParkedOrderActionField;
import xyz.redtorch.api.jctp.td.CThostFtdcRemoveParkedOrderField;
import xyz.redtorch.api.jctp.td.CThostFtdcReqAuthenticateField;
import xyz.redtorch.api.jctp.td.CThostFtdcReqQueryAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcReqRepealField;
import xyz.redtorch.api.jctp.td.CThostFtdcReqTransferField;
import xyz.redtorch.api.jctp.td.CThostFtdcReqUserLoginField;
import xyz.redtorch.api.jctp.td.CThostFtdcRspAuthenticateField;
import xyz.redtorch.api.jctp.td.CThostFtdcRspInfoField;
import xyz.redtorch.api.jctp.td.CThostFtdcRspRepealField;
import xyz.redtorch.api.jctp.td.CThostFtdcRspTransferField;
import xyz.redtorch.api.jctp.td.CThostFtdcRspUserLoginField;
import xyz.redtorch.api.jctp.td.CThostFtdcSecAgentACIDMapField;
import xyz.redtorch.api.jctp.td.CThostFtdcSecAgentCheckModeField;
import xyz.redtorch.api.jctp.td.CThostFtdcSettlementInfoConfirmField;
import xyz.redtorch.api.jctp.td.CThostFtdcSettlementInfoField;
import xyz.redtorch.api.jctp.td.CThostFtdcTradeField;
import xyz.redtorch.api.jctp.td.CThostFtdcTraderApi;
import xyz.redtorch.api.jctp.td.CThostFtdcTraderSpi;
import xyz.redtorch.api.jctp.td.CThostFtdcTradingAccountField;
import xyz.redtorch.api.jctp.td.CThostFtdcTradingAccountPasswordUpdateField;
import xyz.redtorch.api.jctp.td.CThostFtdcTradingCodeField;
import xyz.redtorch.api.jctp.td.CThostFtdcTradingNoticeField;
import xyz.redtorch.api.jctp.td.CThostFtdcTradingNoticeInfoField;
import xyz.redtorch.api.jctp.td.CThostFtdcTransferBankField;
import xyz.redtorch.api.jctp.td.CThostFtdcTransferSerialField;
import xyz.redtorch.api.jctp.td.CThostFtdcUserLogoutField;
import xyz.redtorch.api.jctp.td.CThostFtdcUserPasswordUpdateField;
import xyz.redtorch.api.jctp.td.jctptraderapiv6v3v11x64Constants;
import xyz.redtorch.core.base.RtConstant;
import xyz.redtorch.core.entity.Account;
import xyz.redtorch.core.entity.CancelOrderReq;
import xyz.redtorch.core.entity.Contract;
import xyz.redtorch.core.entity.Order;
import xyz.redtorch.core.entity.OrderReq;
import xyz.redtorch.core.entity.Position;
import xyz.redtorch.core.entity.Trade;

/**
 * @author sun0x00@gmail.com
 */
public class TdSpi extends CThostFtdcTraderSpi {

	private Logger log = LoggerFactory.getLogger(TdSpi.class);
	private CtpGateway ctpGateway;
	// private String mdAddress;
	private String tdAddress;
	private String brokerID;
	private String userID;
	private String password;
	private String authCode;
	private String userProductInfo;
	private String gatewayLogInfo;
	private String gatewayID;
	private String gatewayDisplayName;

	private HashMap<String, Position> positionMap = new HashMap<>();

	private HashMap<String, String> contractExchangeMap;
	private HashMap<String, Integer> contractSizeMap;
	private HashMap<String, String> contractNameMap;
	private HashMap<String, String> originalOrderIDMap = new HashMap<>();

	TdSpi(CtpGateway ctpGateway) {

		this.ctpGateway = ctpGateway;
		// this.mdAddress =
		// ctpGateway.getGatewaySetting().getCtpSetting().getMdAddress();
		this.tdAddress = ctpGateway.getGatewaySetting().getCtpSetting().getTdAddress();
		this.brokerID = ctpGateway.getGatewaySetting().getCtpSetting().getBrokerID();
		this.userID = ctpGateway.getGatewaySetting().getCtpSetting().getUserID();
		this.password = ctpGateway.getGatewaySetting().getCtpSetting().getPassword();
		this.authCode = ctpGateway.getGatewaySetting().getCtpSetting().getAuthCode();
		this.gatewayLogInfo = ctpGateway.getGatewayLogInfo();
		this.gatewayID = ctpGateway.getGatewayID();
		this.gatewayDisplayName = ctpGateway.getGatewayDisplayName();

		this.contractExchangeMap = ctpGateway.getContractExchangeMap();
		this.contractSizeMap = ctpGateway.getContractSizeMap();
		this.contractNameMap = ctpGateway.getContractNameMap();

	}

	private CThostFtdcTraderApi cThostFtdcTraderApi;

	private boolean connectProcessStatus = false; // 避免重复调用
	private boolean connectionStatus = false; // 前置机连接状态
	private boolean loginStatus = false; // 登陆状态
	private String tradingDayStr;

	private boolean instrumentQueried = false;

	private AtomicInteger reqID = new AtomicInteger(0); // 操作请求编号
	private AtomicInteger orderRef = new AtomicInteger(0); // 订单编号

	private boolean authStatus = false; // 验证状态
	private boolean loginFailed = false; // 是否已经使用错误的信息尝试登录过

	private int frontID = 0; // 前置机编号
	private int sessionID = 0; // 会话编号

	private List<Order> orderCacheList = new LinkedList<>(); // 登录起始阶段缓存Order
	private List<Trade> tradeCacheList = new LinkedList<>(); // 登录起始阶段缓存Trade

	/**
	 * 连接
	 */
	public synchronized void connect() {
		if (isConnected() || connectProcessStatus) {
			return;
		}

		if (connectionStatus) {
			login();
			return;
		}
		if (cThostFtdcTraderApi != null) {
			cThostFtdcTraderApi.RegisterSpi(null);
			// 由于CTP底层原因，部分情况下不能正确执行Release
			new Thread() {
				public void run() {

					Thread.currentThread().setName("网关ID-"+gatewayID+"交易接口异步释放线程"+new DateTime().toString(RtConstant.DT_FORMAT_WITH_MS_FORMATTER));
					
					try {
						log.warn("交易接口异步释放启动！");
						cThostFtdcTraderApi.Release();
					} catch (Exception e) {
						log.error("交易接口异步释放发生异常！", e);
					}
				}
			}.start();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// nop
			}
			
			connectionStatus = false;
			loginStatus = false;

		}

		log.warn("{} 交易接口实例初始化",gatewayLogInfo);
		String envTmpDir = System.getProperty("java.io.tmpdir");
		String tempFilePath = envTmpDir + File.separator + "xyz" + File.separator + "redtorch" + File.separator + "api"
				+ File.separator + "jctp" + File.separator + "TEMP_CTP" + File.separator + "TD_"
				+ ctpGateway.getGatewayID() + "_";
		File tempFile = new File(tempFilePath);
		if (!tempFile.getParentFile().exists()) {
			try {
				FileUtils.forceMkdirParent(tempFile);
				log.info("{} 创建临时文件夹 {}", gatewayLogInfo, tempFile.getParentFile().getAbsolutePath());
			} catch (IOException e) {
				log.error("{} 创建临时文件夹失败{}", gatewayLogInfo, tempFile.getParentFile().getAbsolutePath(), e);
			}
		}
		log.info("{} 使用临时文件夹{}", gatewayLogInfo, tempFile.getParentFile().getAbsolutePath());
		cThostFtdcTraderApi = CThostFtdcTraderApi.CreateFtdcTraderApi(tempFile.getAbsolutePath());
		cThostFtdcTraderApi.RegisterSpi(this);
		cThostFtdcTraderApi.RegisterFront(tdAddress);
		connectProcessStatus = true;
		cThostFtdcTraderApi.Init();

	}

	/**
	 * 关闭
	 */
	public synchronized void close() {
		if (cThostFtdcTraderApi != null) {
			log.warn("{} 交易接口实例开始关闭并释放", gatewayLogInfo);
			cThostFtdcTraderApi.RegisterSpi(null);
			
			// 避免异步线程找不到引用
			CThostFtdcTraderApi cThostFtdcTraderApiForRelease = cThostFtdcTraderApi;
			// 由于CTP底层原因，部分情况下不能正确执行Release
			new Thread() {
				public void run() {

					Thread.currentThread().setName("网关ID-"+gatewayID+"交易接口异步释放线程"+new DateTime().toString(RtConstant.DT_FORMAT_WITH_MS_FORMATTER));
					
					try {
						log.warn("交易接口异步释放启动！");
					    cThostFtdcTraderApiForRelease.Release();
					} catch (Exception e) {
						log.error("交易接口异步释放发生异常！", e);
					}
				}
			}.start();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// nop
			}
			
			cThostFtdcTraderApi = null;
			connectionStatus = false;
			instrumentQueried = false;
			loginStatus = false;
			connectProcessStatus = false;
			log.warn("{} 交易接口实例关闭并异步释放", gatewayLogInfo);
			// 通知停止其他关联实例
			ctpGateway.close();
		}else {
			log.warn("{} 交易接口实例为null,无需关闭",gatewayLogInfo);
		}
		
	}

	/**
	 * 返回接口状态
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connectionStatus && loginStatus;
	}

	/**
	 * 获取交易日
	 * 
	 * @return
	 */
	public String getTradingDayStr() {
		return tradingDayStr;
	}

	/**
	 * 查询账户
	 */
	public void queryAccount() {
		if (cThostFtdcTraderApi == null) {
			log.info("{}尚未初始化,无法查询账户", gatewayLogInfo);
			return;
		}
		CThostFtdcQryTradingAccountField cThostFtdcQryTradingAccountField = new CThostFtdcQryTradingAccountField();
		cThostFtdcTraderApi.ReqQryTradingAccount(cThostFtdcQryTradingAccountField, reqID.incrementAndGet());
	}

	/**
	 * 查询持仓
	 */
	public void queryPosition() {
		if (cThostFtdcTraderApi == null) {
			log.info("{}尚未初始化,无法查询持仓", gatewayLogInfo);
			return;
		}

		if (!instrumentQueried) {
			log.info("{}交易接口尚未获取到合约信息,无法查询持仓", gatewayLogInfo);
			return;
		}

		CThostFtdcQryInvestorPositionField cThostFtdcQryInvestorPositionField = new CThostFtdcQryInvestorPositionField();
		// log.info("查询持仓");
		cThostFtdcQryInvestorPositionField.setBrokerID(brokerID);
		cThostFtdcQryInvestorPositionField.setInvestorID(userID);
		cThostFtdcTraderApi.ReqQryInvestorPosition(cThostFtdcQryInvestorPositionField, reqID.incrementAndGet());
	}

	/**
	 * 发单
	 * 
	 * @param orderReq
	 * @return
	 */
	public String sendOrder(OrderReq orderReq) {
		if (cThostFtdcTraderApi == null) {
			log.info("{}尚未初始化,无法发单", gatewayLogInfo);
			return null;
		}
		CThostFtdcInputOrderField cThostFtdcInputOrderField = new CThostFtdcInputOrderField();
		orderRef.incrementAndGet();
		cThostFtdcInputOrderField.setInstrumentID(orderReq.getSymbol());
		cThostFtdcInputOrderField.setLimitPrice(orderReq.getPrice());
		cThostFtdcInputOrderField.setVolumeTotalOriginal(orderReq.getVolume());

		cThostFtdcInputOrderField.setOrderPriceType(
				CtpConstant.priceTypeMap.getOrDefault(orderReq.getPriceType(), Character.valueOf('\0')));
		cThostFtdcInputOrderField
				.setDirection(CtpConstant.directionMap.getOrDefault(orderReq.getDirection(), Character.valueOf('\0')));
		cThostFtdcInputOrderField.setCombOffsetFlag(
				String.valueOf(CtpConstant.offsetMap.getOrDefault(orderReq.getOffset(), Character.valueOf('\0'))));
		cThostFtdcInputOrderField.setOrderRef(orderRef.get() + "");
		cThostFtdcInputOrderField.setInvestorID(userID);
		cThostFtdcInputOrderField.setUserID(userID);
		cThostFtdcInputOrderField.setBrokerID(brokerID);

		cThostFtdcInputOrderField
				.setCombHedgeFlag(String.valueOf(jctptraderapiv6v3v11x64Constants.THOST_FTDC_HF_Speculation));
		cThostFtdcInputOrderField.setContingentCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_CC_Immediately);
		cThostFtdcInputOrderField.setForceCloseReason(jctptraderapiv6v3v11x64Constants.THOST_FTDC_FCC_NotForceClose);
		cThostFtdcInputOrderField.setIsAutoSuspend(0);
		cThostFtdcInputOrderField.setTimeCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_TC_GFD);
		cThostFtdcInputOrderField.setVolumeCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_VC_AV);
		cThostFtdcInputOrderField.setMinVolume(1);

		// 判断FAK FOK市价单
		if (RtConstant.PRICETYPE_FAK.equals(orderReq.getPriceType())) {
			cThostFtdcInputOrderField.setOrderPriceType(jctptraderapiv6v3v11x64Constants.THOST_FTDC_OPT_LimitPrice);
			cThostFtdcInputOrderField.setTimeCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_TC_IOC);
			cThostFtdcInputOrderField.setVolumeCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_VC_AV);
		} else if (RtConstant.PRICETYPE_FOK.equals(orderReq.getPriceType())) {
			cThostFtdcInputOrderField.setOrderPriceType(jctptraderapiv6v3v11x64Constants.THOST_FTDC_OPT_LimitPrice);
			cThostFtdcInputOrderField.setTimeCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_TC_IOC);
			cThostFtdcInputOrderField.setVolumeCondition(jctptraderapiv6v3v11x64Constants.THOST_FTDC_VC_CV);
		}

		cThostFtdcTraderApi.ReqOrderInsert(cThostFtdcInputOrderField, reqID.incrementAndGet());
		String rtOrderID = gatewayID + "." + orderRef.get();

		if (StringUtils.isNotBlank(orderReq.getOriginalOrderID())) {
			originalOrderIDMap.put(rtOrderID, orderReq.getOriginalOrderID());
		}

		return rtOrderID;
	}

	// 撤单
	public void cancelOrder(CancelOrderReq cancelOrderReq) {

		if (cThostFtdcTraderApi == null) {
			log.info("{}尚未初始化,无法撤单", gatewayLogInfo);
			return;
		}
		CThostFtdcInputOrderActionField cThostFtdcInputOrderActionField = new CThostFtdcInputOrderActionField();

		cThostFtdcInputOrderActionField.setInstrumentID(cancelOrderReq.getSymbol());
		cThostFtdcInputOrderActionField.setExchangeID(cancelOrderReq.getExchange());
		cThostFtdcInputOrderActionField.setOrderRef(cancelOrderReq.getOrderID());
		cThostFtdcInputOrderActionField.setFrontID(cancelOrderReq.getFrontID());
		cThostFtdcInputOrderActionField.setSessionID(cancelOrderReq.getSessionID());

		cThostFtdcInputOrderActionField.setActionFlag(jctptraderapiv6v3v11x64Constants.THOST_FTDC_AF_Delete);
		cThostFtdcInputOrderActionField.setBrokerID(brokerID);
		cThostFtdcInputOrderActionField.setInvestorID(userID);

		cThostFtdcTraderApi.ReqOrderAction(cThostFtdcInputOrderActionField, reqID.incrementAndGet());
	}

	private void login() {
		if (loginFailed) {
			log.warn(gatewayLogInfo + "交易接口登录曾发生错误,不再登录,以防被锁");
			return;
		}
		
		if(cThostFtdcTraderApi == null) {
			log.warn("{} 交易接口实例已经释放", gatewayLogInfo);
			return;
		}

		if (StringUtils.isEmpty(brokerID) || StringUtils.isEmpty(userID) || StringUtils.isEmpty(password)) {
			log.error(gatewayLogInfo + "BrokerID UserID Password不允许为空");
			return;
		}

		if (!StringUtils.isEmpty(authCode) && !authStatus) {
			// 验证
			CThostFtdcReqAuthenticateField authenticateField = new CThostFtdcReqAuthenticateField();
			authenticateField.setAuthCode(authCode);
			authenticateField.setUserID(userID);
			authenticateField.setBrokerID(brokerID);
			authenticateField.setUserProductInfo(userProductInfo);
			cThostFtdcTraderApi.ReqAuthenticate(authenticateField, reqID.incrementAndGet());
		} else {
			// 登录
			CThostFtdcReqUserLoginField userLoginField = new CThostFtdcReqUserLoginField();
			userLoginField.setBrokerID(brokerID);
			userLoginField.setUserID(userID);
			userLoginField.setPassword(password);
			cThostFtdcTraderApi.ReqUserLogin(userLoginField, 0);
		}
	}

	// 前置机联机回报
	public void OnFrontConnected() {
		log.info("{} 交易接口前置机已连接",gatewayLogInfo);
		// 修改前置机连接状态为true
		connectionStatus = true;
		connectProcessStatus = false;
		login();
	}

	// 前置机断开回报
	public void OnFrontDisconnected(int nReason) {
		log.info("{} 交易接口前置机已断开, Reason:{}", gatewayLogInfo ,nReason);
		close();
	}

	// 登录回报
	public void OnRspUserLogin(CThostFtdcRspUserLoginField pRspUserLogin, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
		if (pRspInfo.getErrorID() == 0) {
			log.info("{} 交易接口登录成功! TradingDay:{},SessionID:{},BrokerID:{},UserID:{}", gatewayLogInfo,
					pRspUserLogin.getTradingDay(), pRspUserLogin.getSessionID(), pRspUserLogin.getBrokerID(),
					pRspUserLogin.getUserID());
			sessionID = pRspUserLogin.getSessionID();
			frontID = pRspUserLogin.getFrontID();
			// 修改登录状态为true
			loginStatus = true;
			tradingDayStr = pRspUserLogin.getTradingDay();
			log.info("{}交易接口获取到的交易日为{}", gatewayLogInfo, tradingDayStr);

			// 确认结算单
			CThostFtdcSettlementInfoConfirmField settlementInfoConfirmField = new CThostFtdcSettlementInfoConfirmField();
			settlementInfoConfirmField.setBrokerID(brokerID);
			settlementInfoConfirmField.setInvestorID(userID);
			cThostFtdcTraderApi.ReqSettlementInfoConfirm(settlementInfoConfirmField, reqID.incrementAndGet());

		} else {
			log.error("{}交易接口登录回报错误! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
					pRspInfo.getErrorMsg());
			loginFailed = true;
		}

	}

	// 心跳警告
	public void OnHeartBeatWarning(int nTimeLapse) {
		log.warn("{} 交易接口心跳警告, Time Lapse:{}", gatewayLogInfo, nTimeLapse);
	}

	// 登出回报
	public void OnRspUserLogout(CThostFtdcUserLogoutField pUserLogout, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
		if (pRspInfo.getErrorID() != 0) {
			log.info("{}OnRspUserLogout!ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
					pRspInfo.getErrorMsg());
		} else {
			log.info("{}OnRspUserLogout!BrokerID:{},UserID:{}", gatewayLogInfo, pUserLogout.getBrokerID(),
					pUserLogout.getUserID());

		}
		loginStatus = false;
	}

	// 错误回报
	public void OnRspError(CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
		log.error("{}交易接口错误回报!ErrorID:{},ErrorMsg:{},RequestID:{},isLast:{}", gatewayLogInfo, pRspInfo.getErrorID(),
				pRspInfo.getErrorMsg(), nRequestID, bIsLast);

	}

	// 验证客户端回报
	public void OnRspAuthenticate(CThostFtdcRspAuthenticateField pRspAuthenticateField, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {

		if (pRspInfo.getErrorID() == 0) {
			authStatus = true;
			log.info(gatewayLogInfo + "交易接口客户端验证成功");

			login();

		} else {
			log.error("{}交易接口客户端验证失败! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
					pRspInfo.getErrorMsg());
		}

	}

	public void OnRspUserPasswordUpdate(CThostFtdcUserPasswordUpdateField pUserPasswordUpdate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspTradingAccountPasswordUpdate(
			CThostFtdcTradingAccountPasswordUpdateField pTradingAccountPasswordUpdate, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	// 发单错误（柜台）
	public void OnRspOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {

		// 无法获取账户信息,使用userID作为账户ID
		String accountID = userID;
		// 无法获取币种信息
		// String rtAccountID = pOrder.getAccountID() + "." + pOrder.getCurrencyID() +"."+ gatewayID;
		// 使用特定值
		String rtAccountID = userID+".CNY."+gatewayID;

		String symbol = pInputOrder.getInstrumentID();
		String exchange = CtpConstant.exchangeMapReverse.get(pInputOrder.getExchangeID());
		String rtSymbol = symbol + "." + exchange;
		String contractName = contractNameMap.get(symbol);
		String orderID = pInputOrder.getOrderRef();
		String rtOrderID = gatewayID + "." + orderID;
		String direction = CtpConstant.directionMapReverse.getOrDefault(pInputOrder.getDirection(),
				RtConstant.DIRECTION_UNKNOWN);
		String offset = CtpConstant.offsetMapReverse.getOrDefault(pInputOrder.getCombOffsetFlag(),
				RtConstant.OFFSET_UNKNOWN);
		double price = pInputOrder.getLimitPrice();
		int totalVolume = pInputOrder.getVolumeTotalOriginal();
		int tradedVolume = 0;
		String status = RtConstant.STATUS_REJECTED;
		String tradingDay = tradingDayStr;
		String orderDate = null;
		String orderTime = null;
		String cancelTime = null;
		String activeTime = null;
		String updateTime = null;

		String originalOrderID = originalOrderIDMap.get(rtOrderID);

		if (instrumentQueried) {
			ctpGateway.emitOrder(originalOrderID, gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol,
					exchange, rtSymbol, contractName, orderID, rtOrderID, direction, offset, price, totalVolume,
					tradedVolume, status, tradingDay, orderDate, orderTime, cancelTime, activeTime, updateTime, frontID,
					sessionID);
		} else {
			Order order = new Order();
			order.setAllValue(originalOrderID, gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol, exchange,
					rtSymbol, contractName, orderID, rtOrderID, direction, offset, price, totalVolume, tradedVolume,
					status, tradingDay, orderDate, orderTime, cancelTime, activeTime, updateTime, frontID, sessionID);
			orderCacheList.add(order);
		}

		// 发送委托事件
		log.error("{}交易接口发单错误回报(柜台)! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
				pRspInfo.getErrorMsg());

	}

	public void OnRspParkedOrderInsert(CThostFtdcParkedOrderField pParkedOrder, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspParkedOrderAction(CThostFtdcParkedOrderActionField pParkedOrderAction,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	// 撤单错误回报（柜台）
	public void OnRspOrderAction(CThostFtdcInputOrderActionField pInputOrderAction, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {

		log.error("{}交易接口撤单错误（柜台）! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
				pRspInfo.getErrorMsg());
	}

	public void OnRspQueryMaxOrderVolume(CThostFtdcQueryMaxOrderVolumeField pQueryMaxOrderVolume,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	// 确认结算信息回报
	public void OnRspSettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField pSettlementInfoConfirm,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

		if (pRspInfo.getErrorID() == 0) {
			log.warn("{}交易接口结算信息确认完成!", gatewayLogInfo);
		} else {
			log.error("{}交易接口结算信息确认出错! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
					pRspInfo.getErrorMsg());
		}

		// 查询所有合约
		log.warn("{}交易接口开始查询合约信息!", gatewayLogInfo);
		CThostFtdcQryInstrumentField cThostFtdcQryInstrumentField = new CThostFtdcQryInstrumentField();
		cThostFtdcTraderApi.ReqQryInstrument(cThostFtdcQryInstrumentField, reqID.incrementAndGet());

	}

	public void OnRspRemoveParkedOrder(CThostFtdcRemoveParkedOrderField pRemoveParkedOrder,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspRemoveParkedOrderAction(CThostFtdcRemoveParkedOrderActionField pRemoveParkedOrderAction,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspExecOrderInsert(CThostFtdcInputExecOrderField pInputExecOrder, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspExecOrderAction(CThostFtdcInputExecOrderActionField pInputExecOrderAction,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspForQuoteInsert(CThostFtdcInputForQuoteField pInputForQuote, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQuoteInsert(CThostFtdcInputQuoteField pInputQuote, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQuoteAction(CThostFtdcInputQuoteActionField pInputQuoteAction, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspBatchOrderAction(CThostFtdcInputBatchOrderActionField pInputBatchOrderAction,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspOptionSelfCloseInsert(CThostFtdcInputOptionSelfCloseField pInputOptionSelfClose,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspOptionSelfCloseAction(CThostFtdcInputOptionSelfCloseActionField pInputOptionSelfCloseAction,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspCombActionInsert(CThostFtdcInputCombActionField pInputCombAction, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryOrder(CThostFtdcOrderField pOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQryTrade(CThostFtdcTradeField pTrade, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	// 持仓查询回报
	public void OnRspQryInvestorPosition(CThostFtdcInvestorPositionField pInvestorPosition,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {

		if (pInvestorPosition == null || StringUtils.isEmpty(pInvestorPosition.getInstrumentID())) {
			return;
		}
		String symbol = pInvestorPosition.getInstrumentID();
		String rtSymbol = symbol;
		if (contractExchangeMap.containsKey(symbol)) {
			rtSymbol = symbol + "." + contractExchangeMap.get(symbol);
		}

		// 获取持仓缓存
		String posName = gatewayID + "." + rtSymbol + "." + pInvestorPosition.getPosiDirection();

		Integer size = contractSizeMap.get(symbol);

		Position position;
		if (positionMap.containsKey(posName)) {
			position = positionMap.get(posName);
		} else {
			position = new Position();
			positionMap.put(posName, position);
			position.setContractSize(size);
			position.setGatewayID(gatewayID);
			position.setSymbol(symbol);
			position.setExchange(contractExchangeMap.get(symbol));
			position.setRtSymbol(rtSymbol);
			position.setContractName(contractNameMap.get(symbol));
			position.setDirection(
					CtpConstant.posiDirectionMapReverse.getOrDefault(pInvestorPosition.getPosiDirection(), ""));
			position.setRtPositionID(gatewayID + "." + rtSymbol + "." + pInvestorPosition.getPosiDirection());
			
			// 无法获取账户信息,使用userID作为账户ID
			String accountID = userID;
			// 无法获取币种信息
			// 使用特定值
			String rtAccountID = userID+".CNY."+gatewayID;
			
			position.setAccountID(accountID);
			position.setRtAccountID(rtAccountID);
		}

		position.setUseMargin(position.getUseMargin() + pInvestorPosition.getUseMargin());
		position.setExchangeMargin(position.getExchangeMargin() + pInvestorPosition.getExchangeMargin());

		position.setPosition(position.getPosition() + pInvestorPosition.getPosition());

		if (RtConstant.DIRECTION_LONG.equals(position.getDirection())) {
			position.setFrozen(pInvestorPosition.getShortFrozen());
		} else {
			position.setFrozen(pInvestorPosition.getLongFrozen());
		}

		if ("INE".contentEquals(position.getExchange()) || "SHFE".contentEquals(position.getExchange())) {
			// 针对上期所、上期能源持仓的今昨分条返回（有昨仓、无今仓）,读取昨仓数据
			if (pInvestorPosition.getYdPosition() > 0 && pInvestorPosition.getTodayPosition() == 0) {

				position.setYdPosition(position.getYdPosition() + pInvestorPosition.getPosition());

				if (RtConstant.DIRECTION_LONG.equals(position.getDirection())) {
					position.setYdFrozen(position.getYdFrozen() + pInvestorPosition.getShortFrozen());
				} else {
					position.setYdFrozen(position.getYdFrozen() + pInvestorPosition.getLongFrozen());
				}
			} else {
				position.setTdPosition(position.getTdPosition() + pInvestorPosition.getPosition());

				if (RtConstant.DIRECTION_LONG.equals(position.getDirection())) {
					position.setTdFrozen(position.getTdFrozen() + pInvestorPosition.getShortFrozen());
				} else {
					position.setTdFrozen(position.getTdFrozen() + pInvestorPosition.getLongFrozen());
				}
			}
		} else {
			position.setTdPosition(position.getTdPosition() + pInvestorPosition.getTodayPosition());
			position.setYdPosition(position.getPosition() - position.getTdPosition());

			// 中金所优先平今
			if ("CFFEX".equals(position.getExchange())) {
				if (position.getTdPosition() > 0) {
					if (position.getTdPosition() >= position.getFrozen()) {
						position.setTdFrozen(position.getFrozen());
					} else {
						position.setTdFrozen(position.getTdPosition());
						position.setYdFrozen(position.getFrozen() - position.getTdPosition());
					}
				} else {
					position.setYdFrozen(position.getFrozen());
				}
			} else {
				// 除了上面几个交易所之外的交易所，优先平昨
				if (position.getYdPosition() > 0) {
					if (position.getYdPosition() >= position.getFrozen()) {
						position.setYdFrozen(position.getFrozen());
					} else {
						position.setYdFrozen(position.getYdPosition());
						position.setTdFrozen(position.getFrozen() - position.getYdPosition());
					}
				} else {
					position.setTdFrozen(position.getFrozen());
				}
			}

		}

		// 计算成本
		Double cost = position.getPrice() * position.getPosition() * size;
		Double openCost = position.getOpenPrice() * position.getPosition() * size;

		// 汇总总仓
		position.setPositionProfit(position.getPositionProfit() + pInvestorPosition.getPositionProfit());

		// 计算持仓均价
		if (position.getPosition() != 0 && contractSizeMap.containsKey(symbol)) {
			position.setPrice((cost + pInvestorPosition.getPositionCost()) / (position.getPosition() * size));
			position.setOpenPrice((openCost + pInvestorPosition.getOpenCost()) / (position.getPosition() * size));
		}

		// 回报结束
		if (bIsLast) {
			for (Position tmpPosition : positionMap.values()) {
				
				if(tmpPosition.getPosition()!=0) {
				
					tmpPosition.setGatewayDisplayName(gatewayDisplayName);
	
					tmpPosition.setPriceDiff(tmpPosition.getPositionProfit() / tmpPosition.getContractSize()
							/ tmpPosition.getPosition());
	
					if (RtConstant.DIRECTION_LONG.equals(tmpPosition.getDirection()) || (tmpPosition.getPosition() > 0
							&& RtConstant.DIRECTION_NET.equals(tmpPosition.getDirection()))) {
	
						// 计算最新价格
						tmpPosition.setLastPrice(tmpPosition.getPrice() + tmpPosition.getPriceDiff());
						// 计算开仓价格差距
						tmpPosition.setOpenPriceDiff( tmpPosition.getLastPrice() - tmpPosition.getOpenPrice());
						// 计算开仓盈亏
						tmpPosition.setOpenPositionProfit(
								tmpPosition.getOpenPriceDiff() * tmpPosition.getPosition() * tmpPosition.getContractSize());
	
					} else if (RtConstant.DIRECTION_SHORT.equals(tmpPosition.getDirection())
							|| (tmpPosition.getPosition() < 0
									&& RtConstant.DIRECTION_NET.equals(tmpPosition.getDirection()))) {
	
						// 计算最新价格
						tmpPosition.setLastPrice(tmpPosition.getPrice() - tmpPosition.getPriceDiff());
						// 计算开仓价格差距
						tmpPosition.setOpenPriceDiff( tmpPosition.getLastPrice() - tmpPosition.getOpenPrice());
						// 计算开仓盈亏
						tmpPosition.setOpenPositionProfit(
								tmpPosition.getOpenPriceDiff() * tmpPosition.getPosition() * tmpPosition.getContractSize());
	
					}else {
						log.error("{} 计算持仓时发现未处理方向，持仓详情{}",gatewayLogInfo,tmpPosition.toString());
					}
					
					// 计算保最新合约价值
					tmpPosition.setContractValue((tmpPosition.getOpenPrice() + tmpPosition.getOpenPriceDiff())
							* tmpPosition.getContractSize() * tmpPosition.getPosition());
	
					if (tmpPosition.getUseMargin() != 0) {
						tmpPosition.setPositionProfitRatio(tmpPosition.getPositionProfit() / tmpPosition.getUseMargin());
						tmpPosition.setOpenPositionProfitRatio(
								tmpPosition.getOpenPositionProfit() / tmpPosition.getUseMargin());
	
					}
				}

				// 发送持仓事件
				ctpGateway.emitPosition(tmpPosition);
			}

			// 清空缓存
			positionMap = new HashMap<>();
		}

	}

	// 账户查询回报
	public void OnRspQryTradingAccount(CThostFtdcTradingAccountField pTradingAccount, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
		Account account = new Account();
		account.setAccountID(pTradingAccount.getAccountID());
		account.setCurrency(pTradingAccount.getCurrencyID());
		account.setAvailable(pTradingAccount.getAvailable());
		account.setCloseProfit(pTradingAccount.getCloseProfit());
		account.setCommission(pTradingAccount.getCommission());
		account.setGatewayID(gatewayID);
		account.setMargin(pTradingAccount.getCurrMargin());
		account.setPositionProfit(pTradingAccount.getPositionProfit());
		account.setPreBalance(pTradingAccount.getPreBalance());
		account.setRtAccountID(
				pTradingAccount.getAccountID() + "." + pTradingAccount.getCurrencyID() + "." + gatewayID);
		account.setDeposit(pTradingAccount.getDeposit());
		account.setWithdraw(pTradingAccount.getWithdraw());
		double balance = pTradingAccount.getPreBalance() - pTradingAccount.getPreCredit()
				- pTradingAccount.getPreMortgage() + pTradingAccount.getMortgage() - pTradingAccount.getWithdraw()
				+ pTradingAccount.getDeposit() + pTradingAccount.getCloseProfit() + pTradingAccount.getPositionProfit()
				+ pTradingAccount.getCashIn() - pTradingAccount.getCommission();

		account.setBalance(balance);

		account.setGatewayDisplayName(gatewayDisplayName);
		ctpGateway.emitAccount(account);

	}

	public void OnRspQryInvestor(CThostFtdcInvestorField pInvestor, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQryTradingCode(CThostFtdcTradingCodeField pTradingCode, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInstrumentMarginRate(CThostFtdcInstrumentMarginRateField pInstrumentMarginRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInstrumentCommissionRate(CThostFtdcInstrumentCommissionRateField pInstrumentCommissionRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryExchange(CThostFtdcExchangeField pExchange, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQryProduct(CThostFtdcProductField pProduct, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	// 合约查询回报
	public void OnRspQryInstrument(CThostFtdcInstrumentField pInstrument, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
		Contract contract = new Contract();
		contract.setGatewayID(gatewayID);
		contract.setSymbol(pInstrument.getInstrumentID());
		contract.setExchange(CtpConstant.exchangeMapReverse.get(pInstrument.getExchangeID()));
		contract.setRtSymbol(contract.getSymbol() + "." + contract.getExchange());
		contract.setRtContractID(contract.getSymbol() + "." + contract.getExchange() + "." + contract.getGatewayID());
		contract.setName(pInstrument.getInstrumentName());

		contract.setSize(pInstrument.getVolumeMultiple());
		contract.setPriceTick(pInstrument.getPriceTick());
		contract.setStrikePrice(pInstrument.getStrikePrice());
		contract.setProductClass(CtpConstant.productClassMapReverse.getOrDefault(pInstrument.getProductClass(),
				RtConstant.PRODUCT_UNKNOWN));
		contract.setExpiryDate(pInstrument.getExpireDate());
		// 针对商品期权
		contract.setUnderlyingSymbol(pInstrument.getUnderlyingInstrID());
		// contract.setUnderlyingSymbol(pInstrument.getUnderlyingInstrID()+pInstrument.getExpireDate().substring(2,
		// pInstrument.getExpireDate().length()-2));

		if (RtConstant.PRODUCT_OPTION.equals(contract.getProductClass())) {
			if (pInstrument.getOptionsType() == '1') {
				contract.setOptionType(RtConstant.OPTION_CALL);
			} else if (pInstrument.getOptionsType() == '2') {
				contract.setOptionType(RtConstant.OPTION_PUT);
			}
		}
		contractExchangeMap.put(contract.getSymbol(), contract.getExchange());
		contractSizeMap.put(contract.getSymbol(), contract.getSize());
		contractNameMap.put(contract.getSymbol(), contract.getName());

		contract.setGatewayDisplayName(gatewayDisplayName);
		ctpGateway.emitContract(contract);

		if (bIsLast) {
			log.info("{} 交易接口合约信息获取完成!共计{}条", gatewayLogInfo, contractExchangeMap.size());
			instrumentQueried = true;

			log.info("{} 交易接口开始推送缓存Order,共计{}条", gatewayLogInfo, orderCacheList.size());
			for (Order order : orderCacheList) {
				order.setContractName(contractNameMap.get(order.getSymbol()));
				ctpGateway.emitOrder(order);
			}
			orderCacheList.clear();

			log.info("{} 交易接口开始推送缓存Trade,共计{}条", gatewayLogInfo, tradeCacheList.size());
			for (Trade trade : tradeCacheList) {
				trade.setContractName(contractNameMap.get(trade.getSymbol()));
				ctpGateway.emitTrade(trade);
			}
			tradeCacheList.clear();
		}

	}

	public void OnRspQryDepthMarketData(CThostFtdcDepthMarketDataField pDepthMarketData,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQrySettlementInfo(CThostFtdcSettlementInfoField pSettlementInfo, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryTransferBank(CThostFtdcTransferBankField pTransferBank, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInvestorPositionDetail(CThostFtdcInvestorPositionDetailField pInvestorPositionDetail,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryNotice(CThostFtdcNoticeField pNotice, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQrySettlementInfoConfirm(CThostFtdcSettlementInfoConfirmField pSettlementInfoConfirm,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInvestorPositionCombineDetail(
			CThostFtdcInvestorPositionCombineDetailField pInvestorPositionCombineDetail,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryCFMMCTradingAccountKey(CThostFtdcCFMMCTradingAccountKeyField pCFMMCTradingAccountKey,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryEWarrantOffset(CThostFtdcEWarrantOffsetField pEWarrantOffset, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInvestorProductGroupMargin(
			CThostFtdcInvestorProductGroupMarginField pInvestorProductGroupMargin, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryExchangeMarginRate(CThostFtdcExchangeMarginRateField pExchangeMarginRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryExchangeMarginRateAdjust(CThostFtdcExchangeMarginRateAdjustField pExchangeMarginRateAdjust,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryExchangeRate(CThostFtdcExchangeRateField pExchangeRate, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQrySecAgentACIDMap(CThostFtdcSecAgentACIDMapField pSecAgentACIDMap,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryProductExchRate(CThostFtdcProductExchRateField pProductExchRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryProductGroup(CThostFtdcProductGroupField pProductGroup, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryMMInstrumentCommissionRate(
			CThostFtdcMMInstrumentCommissionRateField pMMInstrumentCommissionRate, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryMMOptionInstrCommRate(CThostFtdcMMOptionInstrCommRateField pMMOptionInstrCommRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInstrumentOrderCommRate(CThostFtdcInstrumentOrderCommRateField pInstrumentOrderCommRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQrySecAgentTradingAccount(CThostFtdcTradingAccountField pTradingAccount,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQrySecAgentCheckMode(CThostFtdcSecAgentCheckModeField pSecAgentCheckMode,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryOptionInstrTradeCost(CThostFtdcOptionInstrTradeCostField pOptionInstrTradeCost,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryOptionInstrCommRate(CThostFtdcOptionInstrCommRateField pOptionInstrCommRate,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryExecOrder(CThostFtdcExecOrderField pExecOrder, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQryForQuote(CThostFtdcForQuoteField pForQuote, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQryQuote(CThostFtdcQuoteField pQuote, CThostFtdcRspInfoField pRspInfo, int nRequestID,
			boolean bIsLast) {
	}

	public void OnRspQryOptionSelfClose(CThostFtdcOptionSelfCloseField pOptionSelfClose,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryInvestUnit(CThostFtdcInvestUnitField pInvestUnit, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryCombInstrumentGuard(CThostFtdcCombInstrumentGuardField pCombInstrumentGuard,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryCombAction(CThostFtdcCombActionField pCombAction, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryTransferSerial(CThostFtdcTransferSerialField pTransferSerial, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryAccountregister(CThostFtdcAccountregisterField pAccountregister,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	// 委托回报
	public void OnRtnOrder(CThostFtdcOrderField pOrder) {

		String newRef = pOrder.getOrderRef().replace(" ", "");
		// 更新最大报单编号
		orderRef = new AtomicInteger(Math.max(orderRef.get(), Integer.valueOf(newRef)));

		String symbol = pOrder.getInstrumentID();
		String exchange = CtpConstant.exchangeMapReverse.get(pOrder.getExchangeID());
		String rtSymbol = symbol + "." + exchange;
		String contractName = contractNameMap.get(symbol);
		/*
		 * CTP的报单号一致性维护需要基于frontID, sessionID, orderID三个字段
		 * 但在本接口设计中,已经考虑了CTP的OrderRef的自增性,避免重复 唯一可能出现OrderRef重复的情况是多处登录并在非常接近的时间内（几乎同时发单
		 */

		// 无法获取账户信息,使用userID作为账户ID
		String accountID = userID;
		// 无法获取币种信息
		// String rtAccountID = pOrder.getAccountID() + "." + pOrder.getCurrencyID() +"."+ gatewayID;
		// 使用特定值
		String rtAccountID = userID+".CNY."+gatewayID;

		String orderID = pOrder.getOrderRef();
		String rtOrderID = gatewayID + "." + orderID;
		String direction = CtpConstant.directionMapReverse.get(pOrder.getDirection());
		String offset = CtpConstant.offsetMapReverse.get(pOrder.getCombOffsetFlag().toCharArray()[0]);
		double price = pOrder.getLimitPrice();
		int totalVolume = pOrder.getVolumeTotalOriginal();
		int tradedVolume = pOrder.getVolumeTraded();
		String status = CtpConstant.statusMapReverse.get(pOrder.getOrderStatus());
		String tradingDay = tradingDayStr;
		String orderDate = pOrder.getInsertDate();
		String orderTime = pOrder.getInsertTime();
		String cancelTime = pOrder.getCancelTime();
		String activeTime = pOrder.getActiveTime();
		String updateTime = pOrder.getUpdateTime();
		int frontID = pOrder.getFrontID();
		int sessionID = pOrder.getSessionID();

		String originalOrderID = originalOrderIDMap.get(rtOrderID);

		if (instrumentQueried) {
			ctpGateway.emitOrder(originalOrderID, gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol,
					exchange, rtSymbol, contractName, orderID, rtOrderID, direction, offset, price, totalVolume,
					tradedVolume, status, tradingDay, orderDate, orderTime, cancelTime, activeTime, updateTime, frontID,
					sessionID);
		} else {
			Order order = new Order();
			order.setAllValue(originalOrderID, gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol, exchange,
					rtSymbol, contractName, orderID, rtOrderID, direction, offset, price, totalVolume, tradedVolume,
					status, tradingDay, orderDate, orderTime, cancelTime, activeTime, updateTime, frontID, sessionID);
			orderCacheList.add(order);
		}

	}

	// 成交回报
	public void OnRtnTrade(CThostFtdcTradeField pTrade) {

		String symbol = pTrade.getInstrumentID();
		String exchange = CtpConstant.exchangeMapReverse.get(pTrade.getExchangeID());
		String rtSymbol = pTrade.getInstrumentID() + "." + exchange;
		String contractName = contractNameMap.get(symbol);
		String tradeID = pTrade.getTradeID();
		String rtTradeID = gatewayID + "." + pTrade.getTradeID();
		String orderID = pTrade.getOrderRef();
		String rtOrderID = gatewayID + "." + pTrade.getOrderRef();
		String direction = CtpConstant.directionMapReverse.getOrDefault(pTrade.getDirection(), "");
		String offset = CtpConstant.offsetMapReverse.getOrDefault(pTrade.getOffsetFlag(), "");
		double price = pTrade.getPrice();
		int volume = pTrade.getVolume();
		String tradingDay = tradingDayStr;
		String tradeDate = pTrade.getTradeDate();
		String tradeTime = pTrade.getTradeTime();

		// 除回测外很少用到，不统一解析
		DateTime dateTime = null;

		String originalOrderID = originalOrderIDMap.get(rtOrderID);

		// 无法获取账户信息,使用userID作为账户ID
		String accountID = userID;
		// 无法获取币种信息
		// String rtAccountID = pTrade.getAccountID() + "." + pTrade.getCurrencyID() +"."+ gatewayID;
		// 使用特定值
		String rtAccountID = userID+".CNY."+gatewayID;
		
		if (instrumentQueried) {
			ctpGateway.emitTrade(gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol, exchange, rtSymbol,
					contractName, tradeID, rtTradeID, orderID, rtOrderID, originalOrderID, direction, offset, price,
					volume, tradingDay, tradeDate, tradeTime, dateTime);
		} else {
			Trade trade = new Trade();
			trade.setAllValue(gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol, exchange, rtSymbol,
					contractName, tradeID, rtTradeID, orderID, rtOrderID, originalOrderID, direction, offset, price,
					volume, tradingDay, tradeDate, tradeTime, dateTime);
			tradeCacheList.add(trade);
		}
	}

	// 发单错误回报（交易所）
	public void OnErrRtnOrderInsert(CThostFtdcInputOrderField pInputOrder, CThostFtdcRspInfoField pRspInfo) {

		// 无法获取账户信息,使用userID作为账户ID
		String accountID = userID;
		// 无法获取币种信息
		// String rtAccountID = pOrder.getAccountID() + "." + pOrder.getCurrencyID() +"."+ gatewayID;
		// 使用特定值
		String rtAccountID = userID+".CNY."+gatewayID;

		String symbol = pInputOrder.getInstrumentID();
		String exchange = CtpConstant.exchangeMapReverse.get(pInputOrder.getExchangeID());
		String rtSymbol = symbol + "." + exchange;
		String contractName = contractNameMap.get(symbol);
		String orderID = pInputOrder.getOrderRef();
		String rtOrderID = gatewayID + "." + orderID;
		String direction = CtpConstant.directionMapReverse.get(pInputOrder.getDirection());
		String offset = CtpConstant.offsetMapReverse.get(pInputOrder.getCombOffsetFlag().toCharArray()[0]);
		double price = pInputOrder.getLimitPrice();
		int totalVolume = pInputOrder.getVolumeTotalOriginal();
		int tradedVolume = 0;
		String status = RtConstant.STATUS_REJECTED;
		String tradingDay = tradingDayStr;
		String orderDate = null;
		String orderTime = null;
		String cancelTime = null;
		String activeTime = null;
		String updateTime = null;

		String originalOrderID = originalOrderIDMap.get(rtOrderID);

		if (instrumentQueried) {
			ctpGateway.emitOrder(originalOrderID, gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol,
					exchange, rtSymbol, contractName, orderID, rtOrderID, direction, offset, price, totalVolume,
					tradedVolume, status, tradingDay, orderDate, orderTime, cancelTime, activeTime, updateTime, frontID,
					sessionID);
		} else {
			Order order = new Order();
			order.setAllValue(originalOrderID, gatewayID, gatewayDisplayName, accountID, rtAccountID, symbol, exchange,
					rtSymbol, contractName, orderID, rtOrderID, direction, offset, price, totalVolume, tradedVolume,
					status, tradingDay, orderDate, orderTime, cancelTime, activeTime, updateTime, frontID, sessionID);
			orderCacheList.add(order);
		}

		log.error("{}交易接口发单错误回报（交易所）! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
				pRspInfo.getErrorMsg());

	}

	// 撤单错误回报（交易所）
	public void OnErrRtnOrderAction(CThostFtdcOrderActionField pOrderAction, CThostFtdcRspInfoField pRspInfo) {
		log.error("{}交易接口撤单错误回报（交易所）! ErrorID:{},ErrorMsg:{}", gatewayLogInfo, pRspInfo.getErrorID(),
				pRspInfo.getErrorMsg());
	}

	public void OnRtnInstrumentStatus(CThostFtdcInstrumentStatusField pInstrumentStatus) {
	}

	public void OnRtnBulletin(CThostFtdcBulletinField pBulletin) {
	}

	public void OnRtnTradingNotice(CThostFtdcTradingNoticeInfoField pTradingNoticeInfo) {
	}

	public void OnRtnErrorConditionalOrder(CThostFtdcErrorConditionalOrderField pErrorConditionalOrder) {
	}

	public void OnRtnExecOrder(CThostFtdcExecOrderField pExecOrder) {
	}

	public void OnErrRtnExecOrderInsert(CThostFtdcInputExecOrderField pInputExecOrder,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnExecOrderAction(CThostFtdcExecOrderActionField pExecOrderAction,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnForQuoteInsert(CThostFtdcInputForQuoteField pInputForQuote, CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnRtnQuote(CThostFtdcQuoteField pQuote) {
	}

	public void OnErrRtnQuoteInsert(CThostFtdcInputQuoteField pInputQuote, CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnQuoteAction(CThostFtdcQuoteActionField pQuoteAction, CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnRtnForQuoteRsp(CThostFtdcForQuoteRspField pForQuoteRsp) {
	}

	public void OnRtnCFMMCTradingAccountToken(CThostFtdcCFMMCTradingAccountTokenField pCFMMCTradingAccountToken) {
	}

	public void OnErrRtnBatchOrderAction(CThostFtdcBatchOrderActionField pBatchOrderAction,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnRtnOptionSelfClose(CThostFtdcOptionSelfCloseField pOptionSelfClose) {
	}

	public void OnErrRtnOptionSelfCloseInsert(CThostFtdcInputOptionSelfCloseField pInputOptionSelfClose,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnOptionSelfCloseAction(CThostFtdcOptionSelfCloseActionField pOptionSelfCloseAction,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnRtnCombAction(CThostFtdcCombActionField pCombAction) {
	}

	public void OnErrRtnCombActionInsert(CThostFtdcInputCombActionField pInputCombAction,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnRspQryContractBank(CThostFtdcContractBankField pContractBank, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryParkedOrder(CThostFtdcParkedOrderField pParkedOrder, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryParkedOrderAction(CThostFtdcParkedOrderActionField pParkedOrderAction,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryTradingNotice(CThostFtdcTradingNoticeField pTradingNotice, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryBrokerTradingParams(CThostFtdcBrokerTradingParamsField pBrokerTradingParams,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQryBrokerTradingAlgos(CThostFtdcBrokerTradingAlgosField pBrokerTradingAlgos,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRspQueryCFMMCTradingAccountToken(
			CThostFtdcQueryCFMMCTradingAccountTokenField pQueryCFMMCTradingAccountToken,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRtnFromBankToFutureByBank(CThostFtdcRspTransferField pRspTransfer) {
	}

	public void OnRtnFromFutureToBankByBank(CThostFtdcRspTransferField pRspTransfer) {
	}

	public void OnRtnRepealFromBankToFutureByBank(CThostFtdcRspRepealField pRspRepeal) {
	}

	public void OnRtnRepealFromFutureToBankByBank(CThostFtdcRspRepealField pRspRepeal) {
	}

	public void OnRtnFromBankToFutureByFuture(CThostFtdcRspTransferField pRspTransfer) {
	}

	public void OnRtnFromFutureToBankByFuture(CThostFtdcRspTransferField pRspTransfer) {
	}

	public void OnRtnRepealFromBankToFutureByFutureManual(CThostFtdcRspRepealField pRspRepeal) {
	}

	public void OnRtnRepealFromFutureToBankByFutureManual(CThostFtdcRspRepealField pRspRepeal) {
	}

	public void OnRtnQueryBankBalanceByFuture(CThostFtdcNotifyQueryAccountField pNotifyQueryAccount) {
	}

	public void OnErrRtnBankToFutureByFuture(CThostFtdcReqTransferField pReqTransfer, CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnFutureToBankByFuture(CThostFtdcReqTransferField pReqTransfer, CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnRepealBankToFutureByFutureManual(CThostFtdcReqRepealField pReqRepeal,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnRepealFutureToBankByFutureManual(CThostFtdcReqRepealField pReqRepeal,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnErrRtnQueryBankBalanceByFuture(CThostFtdcReqQueryAccountField pReqQueryAccount,
			CThostFtdcRspInfoField pRspInfo) {
	}

	public void OnRtnRepealFromBankToFutureByFuture(CThostFtdcRspRepealField pRspRepeal) {
	}

	public void OnRtnRepealFromFutureToBankByFuture(CThostFtdcRspRepealField pRspRepeal) {
	}

	public void OnRspFromBankToFutureByFuture(CThostFtdcReqTransferField pReqTransfer, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspFromFutureToBankByFuture(CThostFtdcReqTransferField pReqTransfer, CThostFtdcRspInfoField pRspInfo,
			int nRequestID, boolean bIsLast) {
	}

	public void OnRspQueryBankAccountMoneyByFuture(CThostFtdcReqQueryAccountField pReqQueryAccount,
			CThostFtdcRspInfoField pRspInfo, int nRequestID, boolean bIsLast) {
	}

	public void OnRtnOpenAccountByBank(CThostFtdcOpenAccountField pOpenAccount) {
	}

	public void OnRtnCancelAccountByBank(CThostFtdcCancelAccountField pCancelAccount) {
	}

	public void OnRtnChangeAccountByBank(CThostFtdcChangeAccountField pChangeAccount) {
	}
}