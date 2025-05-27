<?php

/**
 * error code enum class
 */
class ErrorCode {
    // common error codes (10000-10099)
    public const SUCCESS = [0, "Success"];
    public const NETWORK_ERROR = [10001, "Network Error"];
    public const PARAMETER_ERROR = [10002, "Parameter Error"];
    public const API_KEY_EMPTY = [10003, "api-key can't be empty"];
    public const IP_NOT_IN_WHITELIST = [10004, "The current ip is not in the apikey ip whitelist"];
    public const TOO_MANY_REQUESTS = [10005, "Too many requests, please try again later"];
    public const REQUEST_TOO_FREQUENTLY = [10006, "Request too frequently"];
    public const SIGN_SIGNATURE_ERROR = [10007, "Sign signature error"];
    public const VALUE_NOT_COMPLY = [10008, "{value} does not comply with the rule, optional [correctValue]"];

    // market related error codes (20000-20099)
    public const MARKET_NOT_EXISTS = [20001, "Market not exists"];
    public const POSITION_EXCEED_LIMIT = [20002, "The current positions amount has exceeded the maximum open limit, please adjust the risk limit"];
    public const INSUFFICIENT_BALANCE = [20003, "Insufficient balance"];
    public const INSUFFICIENT_TRADER = [20004, "Insufficient Trader"];
    public const INVALID_LEVERAGE = [20005, "Invalid leverage"];
    public const CANNOT_CHANGE_LEVERAGE = [20006, "You can't change leverage or margin mode as there are open orders"];
    public const ORDER_NOT_FOUND = [20007, "Order not found, please try it later"];
    public const INSUFFICIENT_AMOUNT = [20008, "Insufficient amount"];
    public const POSITION_MODE_UPDATE_FAILED = [20009, "Position exists, so positions mode cannot be updated"];
    public const ACTIVATION_FAILED = [20010, "Activation failed, the available balance in the futures account does not meet the conditions for activation of the coupon"];
    public const ACCOUNT_NOT_ALLOWED = [20011, "Account not allowed to trade"];
    public const FUTURES_NOT_ALLOWED = [20012, "This futures does not allow trading"];
    public const ACCOUNT_PENDING_DELETION = [20013, "Function disabled due tp pending account deletion request"];
    public const ACCOUNT_DELETED = [20014, "Account deleted"];
    public const FUTURES_NOT_SUPPORTED = [20015, "This futures is not supported"];

    // trading related error codes (30000-30099)
    public const ORDER_FAILED_LIQUIDATION = [30001, "Failed to order. Please adjust the order price or the leverage as the order price dealt may immediately liquidate."];
    public const PRICE_BELOW_LIQUIDATED = [30002, "Price below liquidated price"];
    public const PRICE_ABOVE_LIQUIDATED = [30003, "Price above liquidated price"];
    public const POSITION_NOT_EXIST = [30004, "Position not exist"];
    public const TRIGGER_PRICE_TOO_CLOSE = [30005, "The trigger price is closer to the current price and may be triggered immediately"];
    public const SELECT_TP_OR_SL = [30006, "Please select TP or SL"];
    public const TP_PRICE_GREATER_THAN_ENTRY = [30007, "TP trigger price is greater than average entry price"];
    public const TP_PRICE_LESS_THAN_ENTRY = [30008, "TP trigger price is less than average entry price"];
    public const SL_PRICE_LESS_THAN_ENTRY = [30009, "SL trigger price is less than average entry price"];
    public const SL_PRICE_GREATER_THAN_ENTRY = [30010, "SL trigger price is greater than average entry price"];
    public const ABNORMAL_ORDER_STATUS = [30011, "Abnormal order status"];
    public const ALREADY_ADDED_TO_FAVORITE = [30012, "Already added to favorite"];
    public const EXCEED_MAX_ORDER_QUANTITY = [30013, "Exceeded the maximum order quantity"];
    public const MAX_BUY_ORDER_PRICE = [30014, "Max Buy Order Price"];
    public const MIN_SELL_ORDER_PRICE = [30015, "Mini Sell Order Price"];
    public const QTY_TOO_SMALL = [30016, "The qty should be larger than"];
    public const QTY_LESS_THAN_MIN = [30017, "The qty cannot be less than the minimum qty"];
    public const REDUCE_ONLY_NO_POSITION = [30018, "Order failed. No position opened. Cancel [Reduce-only] settings and retry later"];
    public const REDUCE_ONLY_SAME_DIRECTION = [30019, "Order failed. A [Reduce-only] order can not be in the same direction as the open position"];
    public const TP_HIGHER_THAN_MARK = [30020, "Trigger price for TP should be higher than mark price"];
    public const TP_LOWER_THAN_MARK = [30021, "Trigger price for TP should be lower than mark price"];
    public const SL_HIGHER_THAN_MARK = [30022, "Trigger price for SL should be higher than mark price"];
    public const SL_LOWER_THAN_MARK = [30023, "Trigger price fo SL should be lower than mark price"];
    public const SL_LOWER_THAN_LIQ = [30024, "Trigger price for SL should be lower than liq price"];
    public const SL_HIGHER_THAN_LIQ = [30025, "Trigger price for SL should be higher than liq price"];
    public const TP_GREATER_THAN_LAST = [30026, "TP price must be greater than last price"];
    public const TP_GREATER_THAN_MARK = [30027, "TP price must be greater than mark price"];
    public const SL_LESS_THAN_LAST = [30028, "SL price must be less than last price"];
    public const SL_LESS_THAN_MARK = [30029, "SL price must be less than mark price"];
    public const SL_GREATER_THAN_LAST = [30030, "SL price must be greater than last price"];
    public const SL_GREATER_THAN_MARK = [30031, "SL price must be greater than mark price"];
    public const TP_LESS_THAN_LAST = [30032, "TP price must be less than last price"];
    public const TP_LESS_THAN_MARK = [30033, "TP price must be less than mark price"];
    public const TP_LESS_THAN_MARK_2 = [30034, "TP price must be less than mark price"];
    public const SL_GREATER_THAN_TRIGGER = [30035, "SL price must be greater than trigger price"];
    public const TP_GREATER_THAN_TRIGGER = [30036, "TP price must be greater than trigger price"];
    public const TP_GREATER_THAN_TRIGGER_2 = [30037, "TP price must be greater than trigger price"];
    public const TP_SL_AMOUNT_TOO_LARGE = [30038, "TP/SL amount must be less than the size of the position"];
    public const ORDER_QTY_TOO_LARGE = [30039, "The order qty can't be greater than the max order qty"];
    public const FUTURES_TRADING_PROHIBITED = [30040, "Futures trading is prohibited, please contact customer service"];
    public const TRIGGER_PRICE_ZERO = [30041, "Trigger price must be greater than 0"];
    public const CLIENT_ID_DUPLICATE = [30042, "Client ID duplicate"];

    // copy trading related error codes (40000-40099)
    public const CANCEL_LEAD_TRADING_FAILED = [40001, "Please cancel open orders and close all positions before canceling lead trading"];
    public const LEAD_AMOUNT_UNDER_LIMIT = [40002, "Lead amount hast to be over the limits"];
    public const LEAD_ORDER_AMOUNT_EXCEED = [40003, "Lead order amount exceeds the limits"];
    public const DUPLICATE_OPERATION = [40004, "Please do not repeat the operation"];
    public const ACTION_NOT_AVAILABLE = [40005, "Action is not available for the current user type"];
    public const SUB_ACCOUNT_LIMIT = [40006, "Sub-account reaches the limit"];
    public const SHARE_SETTLEMENT_PROCESSING = [40007, "Share settlement is being processed,lease try again later"];
    public const TRANSFER_INSUFFICIENT_BALANCE = [40008, "After the transfer, the account balance will be less than the order amount, please enter again"];

    /**
     * get error message by error code
     * 
     * @param int $code error code
     * @return array|null return array contains error code and error message, null if not found
     */
    public static function getByCode(int $code): ?array {
        $reflection = new ReflectionClass(self::class);
        $constants = $reflection->getConstants();
        
        foreach ($constants as $constant) {
            if ($constant[0] === $code) {
                return $constant;
            }
        }
        
        return null;
    }

    /**
     * get error message string by error code
     * 
     * @param int $code error code
     * @return string error message string
     */
    public static function getMessage(int $code): string {
        $error = self::getByCode($code);
        if ($error === null) {
            return "Unknown error code: {$code}";
        }
        return "Error {$error[0]}: {$error[1]}";
    }
} 