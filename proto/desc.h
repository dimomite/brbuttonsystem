// E:\projects\brbuttonsystem\proto\desc.h


char ReportDescriptor[41] = {
    0x06, 0x00, 0xff,              // USAGE_PAGE (Vendor Defined Page 1)
    0x09, 0x01,                    // USAGE (Vendor Usage 1)
    0xa1, 0x02,                    // COLLECTION (Logical)
    0x09, 0x01,                    //   USAGE (Vendor Usage 1)
    0x85, 0x01,                    //   REPORT_ID (1)
    0x15, 0x00,                    //   LOGICAL_MINIMUM (0)
    0x26, 0xff, 0x00,              //   LOGICAL_MAXIMUM (255)
    0x75, 0x08,                    //   REPORT_SIZE (8)
    0x95, 0x3f,                    //   REPORT_COUNT (63)
    0x81, 0x02,                    //   INPUT (Data,Var,Abs)
    0xc0,                          // END_COLLECTION
    0xa1, 0x02,                    // COLLECTION (Logical)
    0x09, 0x01,                    //   USAGE (Vendor Usage 1)
    0x85, 0x02,                    //   REPORT_ID (2)
    0x15, 0x00,                    //   LOGICAL_MINIMUM (0)
    0x26, 0xff, 0x00,              //   LOGICAL_MAXIMUM (255)
    0x75, 0x08,                    //   REPORT_SIZE (8)
    0x95, 0x3f,                    //   REPORT_COUNT (63)
    0x91, 0x02,                    //   OUTPUT (Data,Var,Abs)
    0xc0                           // END_COLLECTION
};

