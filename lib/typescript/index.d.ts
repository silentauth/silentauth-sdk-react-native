export type ErrorResponse = {
    error: string;
    error_description: string;
    debug?: DebugResponse;
};
export type ApiError = {
    status: string;
    type: string;
    title: string;
    detail: string;
};
export type DebugResponse = {
    device_info: string;
    url_trace: string;
};
export type ReachabilitySuccessResponse = {
    http_status: number;
    response_body?: ReachabilityResponseBody;
    debug?: DebugResponse;
};
export type ReachabilityBody = {
    network_id: string;
    network_name: string;
    country_code: string;
    mobile_data_ip: string;
    products?: ReachabilityProduct[];
};
export type ReachabilityProduct = {
    product_id: string;
    product_name: string;
};
export type CheckSuccessResponse = {
    http_status: number;
    response_body?: CheckResponseBody;
    debug?: DebugResponse;
};
export type CheckErrorBody = {
    error: string;
    error_description: string;
    check_id: string;
    reference_id?: string;
};
export type CheckSuccessBody = {
    code: string;
    check_id: string;
    reference_id?: string;
};
export type ReachabilityResponseBody = ReachabilityBody | ApiError;
export type ReachabilityResponse = ReachabilitySuccessResponse | ErrorResponse;
export type CheckResponse = CheckSuccessResponse | ErrorResponse;
export type CheckResponseBody = CheckSuccessBody | CheckErrorBody | ApiError;
type SilentAuthSdkReactNativeType = {
    openWithDataCellular<T>(url: string): Promise<T>;
    openWithDataCellularWithDebug<T>(url: string, debug: boolean): Promise<T>;
    openWithDataCellularAndAccessToken<T>(url: string, debug: boolean, accessToken?: string): Promise<T>;
};
declare const _default: SilentAuthSdkReactNativeType;
export default _default;
//# sourceMappingURL=index.d.ts.map