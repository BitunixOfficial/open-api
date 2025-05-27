package main

import (
	"fmt"
)

// ErrorCode defines the error code structure
type ErrorCode struct {
	Code    int
	Message string
}

// Define all error code constants
var (
	// General error codes (10000-10099)
	SUCCESS                = ErrorCode{0, "Success"}
	NETWORK_ERROR          = ErrorCode{10001, "Network Error"}
	PARAMETER_ERROR        = ErrorCode{10002, "Parameter Error"}
	API_KEY_EMPTY          = ErrorCode{10003, "api-key can't be empty"}
	IP_NOT_IN_WHITELIST    = ErrorCode{10004, "The current ip is not in the apikey ip whitelist"}
	TOO_MANY_REQUESTS      = ErrorCode{10005, "Too many requests, please try again later"}
	REQUEST_TOO_FREQUENTLY = ErrorCode{10006, "Request too frequently"}
	SIGN_SIGNATURE_ERROR   = ErrorCode{10007, "Sign signature error"}
	VALUE_NOT_COMPLY       = ErrorCode{10008, "{value} does not comply with the rule, optional [correctValue]"}

	// Market related error codes (20000-20099)
	MARKET_NOT_EXISTS           = ErrorCode{20001, "Market not exists"}
	POSITION_EXCEED_LIMIT       = ErrorCode{20002, "The current positions amount has exceeded the maximum open limit, please adjust the risk limit"}
	INSUFFICIENT_BALANCE        = ErrorCode{20003, "Insufficient balance"}
	INSUFFICIENT_TRADER         = ErrorCode{20004, "Insufficient Trader"}
	INVALID_LEVERAGE            = ErrorCode{20005, "Invalid leverage"}
	CANNOT_CHANGE_LEVERAGE      = ErrorCode{20006, "You can't change leverage or margin mode as there are open orders"}
	ORDER_NOT_FOUND             = ErrorCode{20007, "Order not found, please try it later"}
	INSUFFICIENT_AMOUNT         = ErrorCode{20008, "Insufficient amount"}
	POSITION_MODE_UPDATE_FAILED = ErrorCode{20009, "Position exists, so positions mode cannot be updated"}
	ACTIVATION_FAILED           = ErrorCode{20010, "Activation failed, the available balance in the futures account does not meet the conditions for activation of the coupon"}
	ACCOUNT_NOT_ALLOWED         = ErrorCode{20011, "Account not allowed to trade"}
	FUTURES_NOT_ALLOWED         = ErrorCode{20012, "This futures does not allow trading"}
	ACCOUNT_PENDING_DELETION    = ErrorCode{20013, "Function disabled due tp pending account deletion request"}
	ACCOUNT_DELETED             = ErrorCode{20014, "Account deleted"}
	FUTURES_NOT_SUPPORTED       = ErrorCode{20015, "This futures is not supported"}

	// Trading related error codes (30000-30099)
	ORDER_FAILED_LIQUIDATION    = ErrorCode{30001, "Failed to order. Please adjust the order price or the leverage as the order price dealt may immediately liquidate."}
	PRICE_BELOW_LIQUIDATED      = ErrorCode{30002, "Price below liquidated price"}
	PRICE_ABOVE_LIQUIDATED      = ErrorCode{30003, "Price above liquidated price"}
	POSITION_NOT_EXIST          = ErrorCode{30004, "Position not exist"}
	TRIGGER_PRICE_TOO_CLOSE     = ErrorCode{30005, "The trigger price is closer to the current price and may be triggered immediately"}
	SELECT_TP_OR_SL             = ErrorCode{30006, "Please select TP or SL"}
	TP_PRICE_GREATER_THAN_ENTRY = ErrorCode{30007, "TP trigger price is greater than average entry price"}
	TP_PRICE_LESS_THAN_ENTRY    = ErrorCode{30008, "TP trigger price is less than average entry price"}
	SL_PRICE_LESS_THAN_ENTRY    = ErrorCode{30009, "SL trigger price is less than average entry price"}
	SL_PRICE_GREATER_THAN_ENTRY = ErrorCode{30010, "SL trigger price is greater than average entry price"}
	ABNORMAL_ORDER_STATUS       = ErrorCode{30011, "Abnormal order status"}
	ALREADY_ADDED_TO_FAVORITE   = ErrorCode{30012, "Already added to favorite"}
	EXCEED_MAX_ORDER_QUANTITY   = ErrorCode{30013, "Exceeded the maximum order quantity"}
	MAX_BUY_ORDER_PRICE         = ErrorCode{30014, "Max Buy Order Price"}
	MIN_SELL_ORDER_PRICE        = ErrorCode{30015, "Mini Sell Order Price"}
	QTY_TOO_SMALL               = ErrorCode{30016, "The qty should be larger than"}
	QTY_LESS_THAN_MIN           = ErrorCode{30017, "The qty cannot be less than the minimum qty"}
	REDUCE_ONLY_NO_POSITION     = ErrorCode{30018, "Order failed. No position opened. Cancel [Reduce-only] settings and retry later"}
	REDUCE_ONLY_SAME_DIRECTION  = ErrorCode{30019, "Order failed. A [Reduce-only] order can not be in the same direction as the open position"}
	TP_HIGHER_THAN_MARK         = ErrorCode{30020, "Trigger price for TP should be higher than mark price"}
	TP_LOWER_THAN_MARK          = ErrorCode{30021, "Trigger price for TP should be lower than mark price"}
	SL_HIGHER_THAN_MARK         = ErrorCode{30022, "Trigger price for SL should be higher than mark price"}
	SL_LOWER_THAN_MARK          = ErrorCode{30023, "Trigger price fo SL should be lower than mark price"}
	SL_LOWER_THAN_LIQ           = ErrorCode{30024, "Trigger price for SL should be lower than liq price"}
	SL_HIGHER_THAN_LIQ          = ErrorCode{30025, "Trigger price for SL should be higher than liq price"}
	TP_GREATER_THAN_LAST        = ErrorCode{30026, "TP price must be greater than last price"}
	TP_GREATER_THAN_MARK        = ErrorCode{30027, "TP price must be greater than mark price"}
	SL_LESS_THAN_LAST           = ErrorCode{30028, "SL price must be less than last price"}
	SL_LESS_THAN_MARK           = ErrorCode{30029, "SL price must be less than mark price"}
	SL_GREATER_THAN_LAST        = ErrorCode{30030, "SL price must be greater than last price"}
	SL_GREATER_THAN_MARK        = ErrorCode{30031, "SL price must be greater than mark price"}
	TP_LESS_THAN_LAST           = ErrorCode{30032, "TP price must be less than last price"}
	TP_LESS_THAN_MARK           = ErrorCode{30033, "TP price must be less than mark price"}
	TP_LESS_THAN_MARK_2         = ErrorCode{30034, "TP price must be less than mark price"}
	SL_GREATER_THAN_TRIGGER     = ErrorCode{30035, "SL price must be greater than trigger price"}
	TP_GREATER_THAN_TRIGGER     = ErrorCode{30036, "TP price must be greater than trigger price"}
	TP_GREATER_THAN_TRIGGER_2   = ErrorCode{30037, "TP price must be greater than trigger price"}
	TP_SL_AMOUNT_TOO_LARGE      = ErrorCode{30038, "TP/SL amount must be less than the size of the position"}
	ORDER_QTY_TOO_LARGE         = ErrorCode{30039, "The order qty can't be greater than the max order qty"}
	FUTURES_TRADING_PROHIBITED  = ErrorCode{30040, "Futures trading is prohibited, please contact customer service"}
	TRIGGER_PRICE_ZERO          = ErrorCode{30041, "Trigger price must be greater than 0"}
	CLIENT_ID_DUPLICATE         = ErrorCode{30042, "Client ID duplicate"}

	// Copy trading related error codes (40000-40099)
	CANCEL_LEAD_TRADING_FAILED    = ErrorCode{40001, "Please cancel open orders and close all positions before canceling lead trading"}
	LEAD_AMOUNT_UNDER_LIMIT       = ErrorCode{40002, "Lead amount hast to be over the limits"}
	LEAD_ORDER_AMOUNT_EXCEED      = ErrorCode{40003, "Lead order amount exceeds the limits"}
	DUPLICATE_OPERATION           = ErrorCode{40004, "Please do not repeat the operation"}
	ACTION_NOT_AVAILABLE          = ErrorCode{40005, "Action is not available for the current user type"}
	SUB_ACCOUNT_LIMIT             = ErrorCode{40006, "Sub-account reaches the limit"}
	SHARE_SETTLEMENT_PROCESSING   = ErrorCode{40007, "Share settlement is being processed,lease try again later"}
	TRANSFER_INSUFFICIENT_BALANCE = ErrorCode{40008, "After the transfer, the account balance will be less than the order amount, please enter again"}
)

// GetByCode gets the corresponding error information based on the error code
func GetByCode(code int) *ErrorCode {
	// Use reflection to get all error code constants
	errorCodes := []ErrorCode{
		SUCCESS, NETWORK_ERROR, PARAMETER_ERROR, API_KEY_EMPTY, IP_NOT_IN_WHITELIST,
		TOO_MANY_REQUESTS, REQUEST_TOO_FREQUENTLY, SIGN_SIGNATURE_ERROR, VALUE_NOT_COMPLY,
		MARKET_NOT_EXISTS, POSITION_EXCEED_LIMIT, INSUFFICIENT_BALANCE, INSUFFICIENT_TRADER,
		INVALID_LEVERAGE, CANNOT_CHANGE_LEVERAGE, ORDER_NOT_FOUND, INSUFFICIENT_AMOUNT,
		POSITION_MODE_UPDATE_FAILED, ACTIVATION_FAILED, ACCOUNT_NOT_ALLOWED, FUTURES_NOT_ALLOWED,
		ACCOUNT_PENDING_DELETION, ACCOUNT_DELETED, FUTURES_NOT_SUPPORTED,
		ORDER_FAILED_LIQUIDATION, PRICE_BELOW_LIQUIDATED, PRICE_ABOVE_LIQUIDATED, POSITION_NOT_EXIST,
		TRIGGER_PRICE_TOO_CLOSE, SELECT_TP_OR_SL, TP_PRICE_GREATER_THAN_ENTRY, TP_PRICE_LESS_THAN_ENTRY,
		SL_PRICE_LESS_THAN_ENTRY, SL_PRICE_GREATER_THAN_ENTRY, ABNORMAL_ORDER_STATUS, ALREADY_ADDED_TO_FAVORITE,
		EXCEED_MAX_ORDER_QUANTITY, MAX_BUY_ORDER_PRICE, MIN_SELL_ORDER_PRICE, QTY_TOO_SMALL,
		QTY_LESS_THAN_MIN, REDUCE_ONLY_NO_POSITION, REDUCE_ONLY_SAME_DIRECTION, TP_HIGHER_THAN_MARK,
		TP_LOWER_THAN_MARK, SL_HIGHER_THAN_MARK, SL_LOWER_THAN_MARK, SL_LOWER_THAN_LIQ, SL_HIGHER_THAN_LIQ,
		TP_GREATER_THAN_LAST, TP_GREATER_THAN_MARK, SL_LESS_THAN_LAST, SL_LESS_THAN_MARK, SL_GREATER_THAN_LAST,
		SL_GREATER_THAN_MARK, TP_LESS_THAN_LAST, TP_LESS_THAN_MARK, TP_LESS_THAN_MARK_2, SL_GREATER_THAN_TRIGGER,
		TP_GREATER_THAN_TRIGGER, TP_GREATER_THAN_TRIGGER_2, TP_SL_AMOUNT_TOO_LARGE, ORDER_QTY_TOO_LARGE,
		FUTURES_TRADING_PROHIBITED, TRIGGER_PRICE_ZERO, CLIENT_ID_DUPLICATE,
		CANCEL_LEAD_TRADING_FAILED, LEAD_AMOUNT_UNDER_LIMIT, LEAD_ORDER_AMOUNT_EXCEED, DUPLICATE_OPERATION,
		ACTION_NOT_AVAILABLE, SUB_ACCOUNT_LIMIT, SHARE_SETTLEMENT_PROCESSING, TRANSFER_INSUFFICIENT_BALANCE,
	}

	for _, err := range errorCodes {
		if err.Code == code {
			return &err
		}
	}
	return nil
}

// String implements the Stringer interface
func (e ErrorCode) String() string {
	return fmt.Sprintf("Error %d: %s", e.Code, e.Message)
}
