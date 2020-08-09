#pragma once

#include <stdint.h>

typedef enum {
    TRT_UNDEFINED = 0,
    TRT_GENERAL_REQUEST = 1,
    TRT_SETTING_REQUEST = 2,
    TRT_GAME_REQUEST = 3,
} TopRequestType_t;

/**
 * Total size - 63 bytes.
 * Original message.
 */
typedef struct 
{
    /**
     * @see TopProtocolType_t
     */
    int8_t requestType;

    int8_t payload[62];
} TopRequest_t;


/**
 * Message for requestType = TRT_GAME_REQUEST.
 */
typedef struct 
{
} GameRequest_t;






