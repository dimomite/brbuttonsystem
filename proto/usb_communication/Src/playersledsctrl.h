/*
 * playersledsctrl.h
 *
 *  Created on: 18 авг. 2019 г.
 *      Author: DiMomite
 */

#ifndef PLAYERSLEDSCTRL_H_
#define PLAYERSLEDSCTRL_H_

#include <stdint.h>

typedef enum {
    PLAYER_UNDEFINED = -1,

    PLAYER_1 = 0,
    PLAYER_2 = 1,
    PLAYER_3 = 2,
    PLAYER_4 = 3,

    PLAYER_RED = PLAYER_1,
    PLAYER_GREEN = PLAYER_2,
    PLAYER_YELLOW = PLAYER_3,
    PLAYER_BLUE = PLAYER_4,
} Players;

typedef enum {
    PLAYER_VIS_ONLY_BUTTON_LIGHT = 1 << 0,
    PLAYER_VIS_ONLY_SYSTEM = 1 << 1,
    PLAYER_VIS_SYSTEM_AND_BUTTON_LIGHT = PLAYER_VIS_ONLY_SYSTEM | PLAYER_VIS_ONLY_BUTTON_LIGHT
} PlayerVisMode;

typedef void(*LedsDataSender)(uint8_t);

typedef struct {
    uint8_t state;
    LedsDataSender sender;
} PlayerLedsCtrl;

void PlayerLeds_Init(PlayerLedsCtrl* ctrl, LedsDataSender sender);
void PlayerLeds_ClearAll(PlayerLedsCtrl* ctrl);
void PlayerLeds_SetPlayer(PlayerLedsCtrl* ctrl, Players player, PlayerVisMode mode);
void PlayerLeds_ClearPlayer(PlayerLedsCtrl* ctrl, Players player);
void PlayerLeds_SetModeForAllPlayers(PlayerLedsCtrl* ctrl, PlayerVisMode mode);
void PlayerLeds_ClearModeOfAllPlayers(PlayerLedsCtrl* ctrl, PlayerVisMode mode);

#endif /* PLAYERSLEDSCTRL_H_ */
