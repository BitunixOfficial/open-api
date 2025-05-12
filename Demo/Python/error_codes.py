from enum import Enum

class ErrorCode(Enum):
    # General error codes (10000-10099)
    SUCCESS = (0, "Success")
    NETWORK_ERROR = (10001, "Network Error")
    PARAMETER_ERROR = (10002, "Parameter Error")
    API_KEY_EMPTY = (10003, "api-key can't be empty")
    IP_NOT_IN_WHITELIST = (10004, "The current ip is not in the apikey ip whitelist")
    TOO_MANY_REQUESTS = (10005, "Too many requests, please try again later")
    REQUEST_TOO_FREQUENTLY = (10006, "Request too frequently")
    SIGN_SIGNATURE_ERROR = (10007, "Sign signature error")
    VALUE_NOT_COMPLY = (10008, "{value} does not comply with the rule, optional [correctValue]")

    # Market related error codes (20000-20099)
    MARKET_NOT_EXISTS = (20001, "Market not exists")
    POSITION_EXCEED_LIMIT = (20002, "The current positions amount has exceeded the maximum open limit, please adjust the risk limit")
    INSUFFICIENT_BALANCE = (20003, "Insufficient balance")
    INSUFFICIENT_TRADER = (20004, "Insufficient Trader")
    INVALID_LEVERAGE = (20005, "Invalid leverage")
    CANNOT_CHANGE_LEVERAGE = (20006, "You can't change leverage or margin mode as there are open orders")
    ORDER_NOT_FOUND = (20007, "Order not found, please try it later")
    INSUFFICIENT_AMOUNT = (20008, "Insufficient amount")
    POSITION_MODE_UPDATE_FAILED = (20009, "Position exists, so positions mode cannot be updated")
    ACTIVATION_FAILED = (20010, "Activation failed, the available balance in the futures account does not meet the conditions for activation of the coupon")
    ACCOUNT_NOT_ALLOWED = (20011, "Account not allowed to trade")
    FUTURES_NOT_ALLOWED = (20012, "This futures does not allow trading")
    ACCOUNT_PENDING_DELETION = (20013, "Function disabled due tp pending account deletion request")
    ACCOUNT_DELETED = (20014, "Account deleted")
    FUTURES_NOT_SUPPORTED = (20015, "This futures is not supported")

    # Trading related error codes (30000-30099)
    ORDER_FAILED_LIQUIDATION = (30001, "Failed to order. Please adjust the order price or the leverage as the order price dealt may immediately liquidate.")
    PRICE_BELOW_LIQUIDATED = (30002, "Price below liquidated price")
    PRICE_ABOVE_LIQUIDATED = (30003, "Price above liquidated price")
    POSITION_NOT_EXIST = (30004, "Position not exist")
    TRIGGER_PRICE_TOO_CLOSE = (30005, "The trigger price is closer to the current price and may be triggered immediately")
    SELECT_TP_OR_SL = (30006, "Please select TP or SL")
    TP_PRICE_GREATER_THAN_ENTRY = (30007, "TP trigger price is greater than average entry price")
    TP_PRICE_LESS_THAN_ENTRY = (30008, "TP trigger price is less than average entry price")
    SL_PRICE_LESS_THAN_ENTRY = (30009, "SL trigger price is less than average entry price")
    SL_PRICE_GREATER_THAN_ENTRY = (30010, "SL trigger price is greater than average entry price")
    ABNORMAL_ORDER_STATUS = (30011, "Abnormal order status")
    ALREADY_ADDED_TO_FAVORITE = (30012, "Already added to favorite")
    EXCEED_MAX_ORDER_QUANTITY = (30013, "Exceeded the maximum order quantity")
    MAX_BUY_ORDER_PRICE = (30014, "Max Buy Order Price")
    MIN_SELL_ORDER_PRICE = (30015, "Mini Sell Order Price")
    QTY_TOO_SMALL = (30016, "The qty should be larger than")
    QTY_LESS_THAN_MIN = (30017, "The qty cannot be less than the minimum qty")
    REDUCE_ONLY_NO_POSITION = (30018, "Order failed. No position opened. Cancel [Reduce-only] settings and retry later")
    REDUCE_ONLY_SAME_DIRECTION = (30019, "Order failed. A [Reduce-only] order can not be in the same direction as the open position")
    TP_HIGHER_THAN_MARK = (30020, "Trigger price for TP should be higher than mark price")
    TP_LOWER_THAN_MARK = (30021, "Trigger price for TP should be lower than mark price")
    SL_HIGHER_THAN_MARK = (30022, "Trigger price for SL should be higher than mark price")
    SL_LOWER_THAN_MARK = (30023, "Trigger price fo SL should be lower than mark price")
    SL_LOWER_THAN_LIQ = (30024, "Trigger price for SL should be lower than liq price")
    SL_HIGHER_THAN_LIQ = (30025, "Trigger price for SL should be higher than liq price")
    TP_GREATER_THAN_LAST = (30026, "TP price must be greater than last price")
    TP_GREATER_THAN_MARK = (30027, "TP price must be greater than mark price")
    SL_LESS_THAN_LAST = (30028, "SL price must be less than last price")
    SL_LESS_THAN_MARK = (30029, "SL price must be less than mark price")
    SL_GREATER_THAN_LAST = (30030, "SL price must be greater than last price")
    SL_GREATER_THAN_MARK = (30031, "SL price must be greater than mark price")
    TP_LESS_THAN_LAST = (30032, "TP price must be less than last price")
    TP_LESS_THAN_MARK = (30033, "TP price must be less than mark price")
    TP_LESS_THAN_MARK_2 = (30034, "TP price must be less than mark price")
    SL_GREATER_THAN_TRIGGER = (30035, "SL price must be greater than trigger price")
    TP_GREATER_THAN_TRIGGER = (30036, "TP price must be greater than trigger price")
    TP_GREATER_THAN_TRIGGER_2 = (30037, "TP price must be greater than trigger price")
    TP_SL_AMOUNT_TOO_LARGE = (30038, "TP/SL amount must be less than the size of the position")
    ORDER_QTY_TOO_LARGE = (30039, "The order qty can't be greater than the max order qty")
    FUTURES_TRADING_PROHIBITED = (30040, "Futures trading is prohibited, please contact customer service")
    TRIGGER_PRICE_ZERO = (30041, "Trigger price must be greater than 0")
    CLIENT_ID_DUPLICATE = (30042, "Client ID duplicate")

    # Copy trading related error codes (40000-40099)
    CANCEL_LEAD_TRADING_FAILED = (40001, "Please cancel open orders and close all positions before canceling lead trading")
    LEAD_AMOUNT_UNDER_LIMIT = (40002, "Lead amount hast to be over the limits")
    LEAD_ORDER_AMOUNT_EXCEED = (40003, "Lead order amount exceeds the limits")
    DUPLICATE_OPERATION = (40004, "Please do not repeat the operation")
    ACTION_NOT_AVAILABLE = (40005, "Action is not available for the current user type")
    SUB_ACCOUNT_LIMIT = (40006, "Sub-account reaches the limit")
    SHARE_SETTLEMENT_PROCESSING = (40007, "Share settlement is being processed,lease try again later")
    TRANSFER_INSUFFICIENT_BALANCE = (40008, "After the transfer, the account balance will be less than the order amount, please enter again")

    def __init__(self, code: int, message: str):
        self.code = code
        self.message = message

    @classmethod
    def get_by_code(cls, code: int) -> 'ErrorCode':
        """Get the corresponding error enum by error code"""
        for error in cls:
            if error.code == code:
                return error
        return None

    def __str__(self):
        return f"Error {self.code}: {self.message}" 