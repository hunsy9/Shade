import axios from 'axios';

export const getQueryConfig = (contentType, responseType) => {
    const headers = Boolean(true)
        ? {
            // 'X-Auth-Token': true,
            'Content-Type': contentType ?? 'application/json'
            // 'Auth-User-Key': authKey,
            // 'Auth-User-Type': authType
        }
        : {};

    axios.defaults.timeout = 1000 * 60; // 60s
    const CancelToken = axios.CancelToken;
    const source = CancelToken.source();
    const cancelToken = source.token;

    return {
        headers,
        cancelToken,
        responseType: responseType ?? 'json'
    };
};