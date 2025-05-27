package com.bitunix.openapi.constants;

public interface FuturesPath {

    String WS_PUBLIC = "/public/";
    String WS_PRIVATE = "/private/";

    String GET_TRADING_PAIRS = "/api/v1/futures/market/trading_pairs";
    String GET_TICKERS = "/api/v1/futures/market/tickers";
    String GET_KLINE = "/api/v1/futures/market/kline";
    String GET_FUNDING_RATE = "/api/v1/futures/market/funding_rate";
    String GET_DEPTH = "/api/v1/futures/market/depth";

    String GET_ACCOUNT = "/api/v1/futures/account";
    String GET_LEVERAGE_AND_MARGIN_MODE = "/api/v1/futures/account/get_leverage_margin_mode";
    String CHANGE_POSITION_MODE = "/api/v1/futures/account/change_position_mode";
    String CHANGE_LEVERAGE = "/api/v1/futures/account/change_leverage";
    String CHANGE_MARGIN_MODE = "/api/v1/futures/account/change_margin_mode";
    String ADJUST_POSITION_MARGIN = "/api/v1/futures/account/adjust_position_margin";
    String PLACE_ORDER = "/api/v1/futures/trade/place_order";
    String BATCH_PLACE_ORDER = "/api/v1/futures/trade/batch_order";
    String CANCEL_ALL_ORDERS = "/api/v1/futures/trade/cancel_all_orders";
    String CANCEL_ORDERS = "/api/v1/futures/trade/cancel_orders";
    String CLOSE_ALL_POSITION = "/api/v1/futures/trade/close_all_position";
    String FLASH_CLOSE_POSITION = "/api/v1/futures/trade/flash_close_position";
    String GET_HISTORY_ORDERS = "/api/v1/futures/trade/get_history_orders";
    String GET_HISTORY_TRADES = "/api/v1/futures/trade/get_history_trades";
    String GET_ORDER_DETAIL = "/api/v1/futures/trade/get_order_detail";
    String GET_PENDING_ORDERS = "/api/v1/futures/trade/get_pending_orders";
    String MODIFY_ORDER = "/api/v1/futures/trade/modify_order";
    String GET_HISTORY_POSITIONS = "/api/v1/futures/position/get_history_positions";
    String GET_PENDING_POSITIONS = "/api/v1/futures/position/get_pending_positions";
    String GET_POSITION_TIERS = "/api/v1/futures/position/get_position_tiers";
    String CANCEL_TPSL_ORDERS = "/api/v1/futures/tpsl/cancel_order";
    String GET_HISTORY_TPSL_ORDERS = "/api/v1/futures/tpsl/get_history_orders";
    String GET_PENDING_TPSL_ORDERS = "/api/v1/futures/tpsl/get_pending_orders";
    String MODIFY_POSITION_TPSL_ORDER = "/api/v1/futures/tpsl/position/modify_order";
    String MODIFY_TPSL_ORDER = "/api/v1/futures/tpsl/modify_order";
    String PLACE_POSITION_TPSL_ORDER = "/api/v1/futures/tpsl/position/place_order";
    String PLACE_TPSL_ORDER = "/api/v1/futures/tpsl/place_order";

}
