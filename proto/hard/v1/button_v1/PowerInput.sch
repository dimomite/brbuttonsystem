EESchema Schematic File Version 4
LIBS:button_v1-cache
EELAYER 29 0
EELAYER END
$Descr A4 11693 8268
encoding utf-8
Sheet 3 3
Title ""
Date ""
Rev ""
Comp ""
Comment1 ""
Comment2 ""
Comment3 ""
Comment4 ""
$EndDescr
$Comp
L Connector:USB_B_Micro J?
U 1 1 5D41A7E3
P 1300 1500
AR Path="/5D41A7E3" Ref="J?"  Part="1" 
AR Path="/5D1C99DD/5D41A7E3" Ref="J5"  Part="1" 
AR Path="/5D4A520B/5D41A7E3" Ref="J5"  Part="1" 
F 0 "J5" H 1357 1967 50  0000 C CNN
F 1 "USB_B_Micro" H 1357 1876 50  0000 C CNN
F 2 "Connector_USB:USB_Micro-B_Wuerth_629105150521" H 1450 1450 50  0001 C CNN
F 3 "~" H 1450 1450 50  0001 C CNN
	1    1300 1500
	1    0    0    -1  
$EndComp
$Comp
L Device:R R?
U 1 1 5D41A7E9
P 2050 1500
AR Path="/5D41A7E9" Ref="R?"  Part="1" 
AR Path="/5D1C99DD/5D41A7E9" Ref="R1"  Part="1" 
AR Path="/5D4A520B/5D41A7E9" Ref="R1"  Part="1" 
F 0 "R1" V 1950 1400 50  0000 C CNN
F 1 "20R" V 1950 1550 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 1980 1500 50  0001 C CNN
F 3 "~" H 2050 1500 50  0001 C CNN
	1    2050 1500
	0    1    1    0   
$EndComp
$Comp
L Device:R R?
U 1 1 5D41A7EF
P 2050 1600
AR Path="/5D41A7EF" Ref="R?"  Part="1" 
AR Path="/5D1C99DD/5D41A7EF" Ref="R2"  Part="1" 
AR Path="/5D4A520B/5D41A7EF" Ref="R2"  Part="1" 
F 0 "R2" V 1950 1500 50  0000 C CNN
F 1 "20R" V 1950 1650 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 1980 1600 50  0001 C CNN
F 3 "~" H 2050 1600 50  0001 C CNN
	1    2050 1600
	0    1    -1   0   
$EndComp
$Comp
L power:GND #PWR?
U 1 1 5D41A7F5
P 1300 2000
AR Path="/5D41A7F5" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D41A7F5" Ref="#PWR0107"  Part="1" 
AR Path="/5D4A520B/5D41A7F5" Ref="#PWR0105"  Part="1" 
F 0 "#PWR0105" H 1300 1750 50  0001 C CNN
F 1 "GND" H 1305 1827 50  0001 C CNN
F 2 "" H 1300 2000 50  0001 C CNN
F 3 "" H 1300 2000 50  0001 C CNN
	1    1300 2000
	1    0    0    -1  
$EndComp
Wire Wire Line
	1300 2000 1300 1900
Wire Wire Line
	1300 1900 1200 1900
Connection ~ 1300 1900
$Comp
L power:+5V #PWR?
U 1 1 5D41A7FE
P 1700 1300
AR Path="/5D41A7FE" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D41A7FE" Ref="#PWR0108"  Part="1" 
AR Path="/5D4A520B/5D41A7FE" Ref="#PWR0107"  Part="1" 
F 0 "#PWR0107" H 1700 1150 50  0001 C CNN
F 1 "+5V" H 1715 1473 50  0000 C CNN
F 2 "" H 1700 1300 50  0001 C CNN
F 3 "" H 1700 1300 50  0001 C CNN
	1    1700 1300
	1    0    0    -1  
$EndComp
Wire Wire Line
	1700 1300 1600 1300
Wire Wire Line
	2200 1600 2400 1600
Wire Wire Line
	2200 1500 2300 1500
$Comp
L Device:R R?
U 1 1 5D41A809
P 2300 1250
AR Path="/5D41A809" Ref="R?"  Part="1" 
AR Path="/5D1C99DD/5D41A809" Ref="R3"  Part="1" 
AR Path="/5D4A520B/5D41A809" Ref="R3"  Part="1" 
F 0 "R3" V 2200 1150 50  0000 C CNN
F 1 "1k5" V 2200 1300 50  0000 C CNN
F 2 "Resistor_SMD:R_0805_2012Metric_Pad1.15x1.40mm_HandSolder" V 2230 1250 50  0001 C CNN
F 3 "~" H 2300 1250 50  0001 C CNN
	1    2300 1250
	-1   0    0    1   
$EndComp
Wire Wire Line
	2300 1400 2300 1500
Connection ~ 2300 1500
Wire Wire Line
	2300 1500 2400 1500
$Comp
L power:+5V #PWR?
U 1 1 5D41A812
P 2300 1100
AR Path="/5D41A812" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D41A812" Ref="#PWR0109"  Part="1" 
AR Path="/5D4A520B/5D41A812" Ref="#PWR0108"  Part="1" 
F 0 "#PWR0108" H 2300 950 50  0001 C CNN
F 1 "+5V" H 2315 1273 50  0000 C CNN
F 2 "" H 2300 1100 50  0001 C CNN
F 3 "" H 2300 1100 50  0001 C CNN
	1    2300 1100
	1    0    0    -1  
$EndComp
Text Label 1650 1500 0    50   ~ 0
USB_P
Text Label 1650 1600 0    50   ~ 0
USB_N
Wire Wire Line
	1600 1500 1900 1500
Wire Wire Line
	1600 1600 1900 1600
Text GLabel 2400 1500 2    50   Input ~ 0
D+
Text GLabel 2400 1600 2    50   Input ~ 0
D-
$Comp
L brbuttonsystem:RClamp0554S U?
U 1 1 5D44DF17
P 3200 1350
AR Path="/5D44DF17" Ref="U?"  Part="1" 
AR Path="/5D1C99DD/5D44DF17" Ref="U7"  Part="1" 
AR Path="/5D4A520B/5D44DF17" Ref="U4"  Part="1" 
F 0 "U4" H 3200 1400 50  0000 C CNN
F 1 "RClamp0554S" H 3550 1400 50  0000 C CNN
F 2 "Package_TO_SOT_SMD:SOT-23-6_Handsoldering" H 3200 1350 50  0001 C CNN
F 3 "" H 3200 1350 50  0001 C CNN
	1    3200 1350
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR?
U 1 1 5D44DF1D
P 3800 1550
AR Path="/5D44DF1D" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D44DF1D" Ref="#PWR0110"  Part="1" 
AR Path="/5D4A520B/5D44DF1D" Ref="#PWR0109"  Part="1" 
F 0 "#PWR0109" H 3800 1300 50  0001 C CNN
F 1 "GND" H 3805 1377 50  0001 C CNN
F 2 "" H 3800 1550 50  0001 C CNN
F 3 "" H 3800 1550 50  0001 C CNN
	1    3800 1550
	0    -1   -1   0   
$EndComp
$Comp
L power:+5V #PWR?
U 1 1 5D44DF23
P 3100 1550
AR Path="/5D44DF23" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D44DF23" Ref="#PWR0111"  Part="1" 
AR Path="/5D4A520B/5D44DF23" Ref="#PWR0110"  Part="1" 
F 0 "#PWR0110" H 3100 1400 50  0001 C CNN
F 1 "+5V" H 3115 1723 50  0000 C CNN
F 2 "" H 3100 1550 50  0001 C CNN
F 3 "" H 3100 1550 50  0001 C CNN
	1    3100 1550
	0    -1   -1   0   
$EndComp
Text Label 3800 1450 0    50   ~ 0
USB_N
Text Label 3800 1650 0    50   ~ 0
USB_P
$Comp
L Regulator_Linear:AP1117-33 U?
U 1 1 5D468D50
P 5550 1350
AR Path="/5D468D50" Ref="U?"  Part="1" 
AR Path="/5D1C99DD/5D468D50" Ref="U3"  Part="1" 
AR Path="/5D4A520B/5D468D50" Ref="U7"  Part="1" 
F 0 "U7" H 5550 1592 50  0000 C CNN
F 1 "AP1117-33" H 5550 1501 50  0000 C CNN
F 2 "Package_TO_SOT_SMD:SOT-223-3_TabPin2" H 5550 1550 50  0001 C CNN
F 3 "http://www.diodes.com/datasheets/AP1117.pdf" H 5650 1100 50  0001 C CNN
	1    5550 1350
	1    0    0    -1  
$EndComp
$Comp
L power:+5V #PWR?
U 1 1 5D468D56
P 5000 1250
AR Path="/5D468D56" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D468D56" Ref="#PWR0112"  Part="1" 
AR Path="/5D4A520B/5D468D56" Ref="#PWR0111"  Part="1" 
F 0 "#PWR0111" H 5000 1100 50  0001 C CNN
F 1 "+5V" H 5000 1400 50  0000 C CNN
F 2 "" H 5000 1250 50  0001 C CNN
F 3 "" H 5000 1250 50  0001 C CNN
	1    5000 1250
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR?
U 1 1 5D468D5C
P 5550 1650
AR Path="/5D468D5C" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D468D5C" Ref="#PWR0123"  Part="1" 
AR Path="/5D4A520B/5D468D5C" Ref="#PWR0112"  Part="1" 
F 0 "#PWR0112" H 5550 1400 50  0001 C CNN
F 1 "GND" H 5555 1477 50  0001 C CNN
F 2 "" H 5550 1650 50  0001 C CNN
F 3 "" H 5550 1650 50  0001 C CNN
	1    5550 1650
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR?
U 1 1 5D468D62
P 6050 1200
AR Path="/5D468D62" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D468D62" Ref="#PWR0124"  Part="1" 
AR Path="/5D4A520B/5D468D62" Ref="#PWR0123"  Part="1" 
F 0 "#PWR0123" H 6050 1050 50  0001 C CNN
F 1 "+3.3V" H 5900 1350 50  0000 L CNN
F 2 "" H 6050 1200 50  0001 C CNN
F 3 "" H 6050 1200 50  0001 C CNN
	1    6050 1200
	1    0    0    -1  
$EndComp
$Comp
L Device:C C?
U 1 1 5D468D68
P 2500 3200
AR Path="/5D468D68" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D468D68" Ref="C7"  Part="1" 
AR Path="/5D4A520B/5D468D68" Ref="C8"  Part="1" 
F 0 "C8" H 2615 3246 50  0000 L CNN
F 1 "1uF" H 2615 3155 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 2538 3050 50  0001 C CNN
F 3 "~" H 2500 3200 50  0001 C CNN
	1    2500 3200
	1    0    0    -1  
$EndComp
Wire Wire Line
	5250 1350 5000 1350
Wire Wire Line
	5000 1250 5000 1350
Connection ~ 5000 1350
Wire Wire Line
	5000 1650 5550 1650
Connection ~ 5550 1650
Wire Wire Line
	5850 1350 6050 1350
Wire Wire Line
	6050 1350 6050 1200
$Comp
L brbuttonsystem:RT8009-33 U?
U 1 1 5D48B29B
P 2950 2950
AR Path="/5D48B29B" Ref="U?"  Part="1" 
AR Path="/5D1C99DD/5D48B29B" Ref="U4"  Part="1" 
AR Path="/5D4A520B/5D48B29B" Ref="U3"  Part="1" 
F 0 "U3" H 3250 3115 50  0000 C CNN
F 1 "RT8009-33" H 3250 3024 50  0000 C CNN
F 2 "Package_TO_SOT_SMD:SOT-23-5_HandSoldering" H 2950 2950 50  0001 C CNN
F 3 "" H 2950 2950 50  0001 C CNN
	1    2950 2950
	1    0    0    -1  
$EndComp
$Comp
L Device:L L?
U 1 1 5D48B2A1
P 3900 3050
AR Path="/5D48B2A1" Ref="L?"  Part="1" 
AR Path="/5D1C99DD/5D48B2A1" Ref="L1"  Part="1" 
AR Path="/5D4A520B/5D48B2A1" Ref="L1"  Part="1" 
F 0 "L1" V 4000 3150 50  0000 C CNN
F 1 "4.7uH" V 4000 2950 50  0000 C CNN
F 2 "Inductor_SMD:L_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 3900 3050 50  0001 C CNN
F 3 "~" H 3900 3050 50  0001 C CNN
	1    3900 3050
	0    -1   -1   0   
$EndComp
Wire Wire Line
	3650 3050 3750 3050
Wire Wire Line
	3650 3150 4050 3150
Wire Wire Line
	4050 3150 4050 3050
Connection ~ 4050 3150
$Comp
L power:GND #PWR?
U 1 1 5D48B2AB
P 3250 3350
AR Path="/5D48B2AB" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2AB" Ref="#PWR0105"  Part="1" 
AR Path="/5D4A520B/5D48B2AB" Ref="#PWR0124"  Part="1" 
F 0 "#PWR0124" H 3250 3100 50  0001 C CNN
F 1 "GND" H 3255 3177 50  0001 C CNN
F 2 "" H 3250 3350 50  0001 C CNN
F 3 "" H 3250 3350 50  0001 C CNN
	1    3250 3350
	1    0    0    -1  
$EndComp
$Comp
L Device:C C?
U 1 1 5D48B2B1
P 5000 1500
AR Path="/5D48B2B1" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D48B2B1" Ref="C5"  Part="1" 
AR Path="/5D4A520B/5D48B2B1" Ref="C5"  Part="1" 
F 0 "C5" H 5115 1546 50  0000 L CNN
F 1 "1uF" H 5115 1455 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 5038 1350 50  0001 C CNN
F 3 "~" H 5000 1500 50  0001 C CNN
	1    5000 1500
	1    0    0    -1  
$EndComp
$Comp
L Device:CP C?
U 1 1 5D48B2B7
P 1300 3250
AR Path="/5D48B2B7" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D48B2B7" Ref="C3"  Part="1" 
AR Path="/5D4A520B/5D48B2B7" Ref="C3"  Part="1" 
F 0 "C3" H 1418 3296 50  0000 L CNN
F 1 "100uF" H 1418 3205 50  0000 L CNN
F 2 "Capacitor_SMD:C_Elec_6.3x7.7" H 1338 3100 50  0001 C CNN
F 3 "~" H 1300 3250 50  0001 C CNN
	1    1300 3250
	1    0    0    -1  
$EndComp
Wire Wire Line
	2850 3050 2500 3050
Connection ~ 2500 3050
Wire Wire Line
	2500 3050 2000 3050
Wire Wire Line
	2500 3350 2000 3350
Wire Wire Line
	2850 3150 2850 3050
Connection ~ 2850 3050
Wire Wire Line
	2500 3350 3250 3350
Connection ~ 2500 3350
Connection ~ 3250 3350
$Comp
L power:+5V #PWR?
U 1 1 5D48B2C6
P 1300 3100
AR Path="/5D48B2C6" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2C6" Ref="#PWR0125"  Part="1" 
AR Path="/5D4A520B/5D48B2C6" Ref="#PWR0125"  Part="1" 
F 0 "#PWR0125" H 1300 2950 50  0001 C CNN
F 1 "+5V" H 1315 3273 50  0000 C CNN
F 2 "" H 1300 3100 50  0001 C CNN
F 3 "" H 1300 3100 50  0001 C CNN
	1    1300 3100
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR?
U 1 1 5D48B2CC
P 1300 3400
AR Path="/5D48B2CC" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2CC" Ref="#PWR0126"  Part="1" 
AR Path="/5D4A520B/5D48B2CC" Ref="#PWR0126"  Part="1" 
F 0 "#PWR0126" H 1300 3150 50  0001 C CNN
F 1 "GND" H 1305 3227 50  0001 C CNN
F 2 "" H 1300 3400 50  0001 C CNN
F 3 "" H 1300 3400 50  0001 C CNN
	1    1300 3400
	1    0    0    -1  
$EndComp
$Comp
L Device:CP C?
U 1 1 5D48B2D2
P 2000 3200
AR Path="/5D48B2D2" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D48B2D2" Ref="C4"  Part="1" 
AR Path="/5D4A520B/5D48B2D2" Ref="C4"  Part="1" 
F 0 "C4" H 2118 3246 50  0000 L CNN
F 1 "2.2uF" H 2118 3155 50  0000 L CNN
F 2 "Capacitor_Tantalum_SMD:CP_EIA-3216-18_Kemet-A_Pad1.58x1.35mm_HandSolder" H 2038 3050 50  0001 C CNN
F 3 "~" H 2000 3200 50  0001 C CNN
	1    2000 3200
	1    0    0    -1  
$EndComp
$Comp
L power:+5V #PWR?
U 1 1 5D48B2D8
P 2000 3050
AR Path="/5D48B2D8" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2D8" Ref="#PWR0127"  Part="1" 
AR Path="/5D4A520B/5D48B2D8" Ref="#PWR0127"  Part="1" 
F 0 "#PWR0127" H 2000 2900 50  0001 C CNN
F 1 "+5V" H 2015 3223 50  0000 C CNN
F 2 "" H 2000 3050 50  0001 C CNN
F 3 "" H 2000 3050 50  0001 C CNN
	1    2000 3050
	1    0    0    -1  
$EndComp
Connection ~ 2000 3050
$Comp
L Device:C C?
U 1 1 5D48B2DF
P 4050 3300
AR Path="/5D48B2DF" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D48B2DF" Ref="C6"  Part="1" 
AR Path="/5D4A520B/5D48B2DF" Ref="C6"  Part="1" 
F 0 "C6" H 4165 3346 50  0000 L CNN
F 1 "10uF" H 4165 3255 50  0000 L CNN
F 2 "Capacitor_SMD:C_0805_2012Metric_Pad1.15x1.40mm_HandSolder" H 4088 3150 50  0001 C CNN
F 3 "~" H 4050 3300 50  0001 C CNN
	1    4050 3300
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR?
U 1 1 5D48B2E5
P 4050 3450
AR Path="/5D48B2E5" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2E5" Ref="#PWR0128"  Part="1" 
AR Path="/5D4A520B/5D48B2E5" Ref="#PWR0128"  Part="1" 
F 0 "#PWR0128" H 4050 3200 50  0001 C CNN
F 1 "GND" H 4055 3277 50  0001 C CNN
F 2 "" H 4050 3450 50  0001 C CNN
F 3 "" H 4050 3450 50  0001 C CNN
	1    4050 3450
	1    0    0    -1  
$EndComp
$Comp
L Device:CP C?
U 1 1 5D48B2EB
P 4600 3300
AR Path="/5D48B2EB" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D48B2EB" Ref="C8"  Part="1" 
AR Path="/5D4A520B/5D48B2EB" Ref="C7"  Part="1" 
F 0 "C7" H 4718 3346 50  0000 L CNN
F 1 "100uF" H 4718 3255 50  0000 L CNN
F 2 "Capacitor_SMD:C_Elec_6.3x7.7" H 4638 3150 50  0001 C CNN
F 3 "~" H 4600 3300 50  0001 C CNN
	1    4600 3300
	1    0    0    -1  
$EndComp
$Comp
L power:+3.3V #PWR?
U 1 1 5D48B2F1
P 4600 3150
AR Path="/5D48B2F1" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2F1" Ref="#PWR0129"  Part="1" 
AR Path="/5D4A520B/5D48B2F1" Ref="#PWR0129"  Part="1" 
F 0 "#PWR0129" H 4600 3000 50  0001 C CNN
F 1 "+3.3V" H 4450 3300 50  0000 L CNN
F 2 "" H 4600 3150 50  0001 C CNN
F 3 "" H 4600 3150 50  0001 C CNN
	1    4600 3150
	1    0    0    -1  
$EndComp
$Comp
L power:GND #PWR?
U 1 1 5D48B2F7
P 4600 3450
AR Path="/5D48B2F7" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B2F7" Ref="#PWR0130"  Part="1" 
AR Path="/5D4A520B/5D48B2F7" Ref="#PWR0130"  Part="1" 
F 0 "#PWR0130" H 4600 3200 50  0001 C CNN
F 1 "GND" H 4605 3277 50  0001 C CNN
F 2 "" H 4600 3450 50  0001 C CNN
F 3 "" H 4600 3450 50  0001 C CNN
	1    4600 3450
	1    0    0    -1  
$EndComp
Wire Wire Line
	4050 3150 4600 3150
Connection ~ 4600 3150
$Comp
L Device:CP C?
U 1 1 5D48B2FF
P 5100 3300
AR Path="/5D48B2FF" Ref="C?"  Part="1" 
AR Path="/5D1C99DD/5D48B2FF" Ref="C10"  Part="1" 
AR Path="/5D4A520B/5D48B2FF" Ref="C10"  Part="1" 
F 0 "C10" H 5218 3346 50  0000 L CNN
F 1 "2.2uF" H 5218 3255 50  0000 L CNN
F 2 "Capacitor_Tantalum_SMD:CP_EIA-3216-18_Kemet-A_Pad1.58x1.35mm_HandSolder" H 5138 3150 50  0001 C CNN
F 3 "~" H 5100 3300 50  0001 C CNN
	1    5100 3300
	1    0    0    -1  
$EndComp
Wire Wire Line
	4600 3150 5100 3150
$Comp
L power:GND #PWR?
U 1 1 5D48B306
P 5100 3450
AR Path="/5D48B306" Ref="#PWR?"  Part="1" 
AR Path="/5D1C99DD/5D48B306" Ref="#PWR0131"  Part="1" 
AR Path="/5D4A520B/5D48B306" Ref="#PWR0131"  Part="1" 
F 0 "#PWR0131" H 5100 3200 50  0001 C CNN
F 1 "GND" H 5105 3277 50  0001 C CNN
F 2 "" H 5100 3450 50  0001 C CNN
F 3 "" H 5100 3450 50  0001 C CNN
	1    5100 3450
	1    0    0    -1  
$EndComp
$EndSCHEMATC
