package ca.gammon.advent2023.days;

import ca.gammon.advent2023.days.pojos.Coordinate;
import ca.gammon.advent2023.days.pojos.Direction;
import ca.gammon.advent2023.days.pojos.Grid;
import ca.gammon.advent2023.days.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day18 {

    private static final int EXPECTED_RESULT_DEMO_PART_ONE = 62;
    private static final int EXPECTED_RESULT_DEMO_PART_TWO = 0;
    private static final int EXPECTED_RESULT_PART_ONE = 0;
    private static final int EXPECTED_RESULT_PART_TWO = 0;

    private static final String DEMO_DATA;
    private static final String DATA;
    static {
        //region DEMO_DATA
        DEMO_DATA = """
                R 6 (#70c710)
                D 5 (#0dc571)
                L 2 (#5713f0)
                D 2 (#d2c081)
                R 2 (#59c680)
                D 2 (#411b91)
                L 5 (#8ceee2)
                U 2 (#caa173)
                L 1 (#1b58a2)
                U 2 (#caa171)
                R 2 (#7807d2)
                U 3 (#a77fa3)
                L 2 (#015232)
                U 2 (#7a21e3)""";
        //endregion
        //region DATA
        DATA = """
                R 6 (#0e4c90)
                U 2 (#5b83a3)
                R 7 (#1a3a90)
                U 2 (#64e9b1)
                R 8 (#27d840)
                D 7 (#069321)
                R 3 (#08be10)
                D 8 (#0cb9c3)
                R 7 (#372230)
                U 5 (#0cb9c1)
                L 4 (#3474a0)
                U 4 (#069323)
                R 4 (#1c9780)
                U 6 (#64e9b3)
                R 5 (#013fd0)
                U 3 (#4370b3)
                L 4 (#34d160)
                U 3 (#1aa763)
                R 6 (#42e460)
                U 7 (#3fe1a3)
                R 6 (#1aac50)
                U 3 (#564173)
                R 5 (#281990)
                U 3 (#50fd73)
                L 7 (#0fdfe0)
                U 3 (#1aa4a3)
                L 7 (#5db330)
                U 7 (#4a6163)
                L 3 (#6c2ee0)
                U 3 (#05d5b3)
                L 4 (#205c00)
                D 5 (#35a813)
                L 6 (#38a3a0)
                D 9 (#1d7ee3)
                L 9 (#38a3a2)
                U 7 (#247a93)
                L 4 (#2d4112)
                U 7 (#33c8e3)
                L 7 (#523732)
                U 8 (#2fc5e1)
                L 5 (#3d1042)
                D 8 (#2fc5e3)
                L 4 (#037172)
                D 6 (#144643)
                L 2 (#4bfe32)
                D 8 (#144641)
                L 4 (#3a6a12)
                D 4 (#3fafa3)
                R 6 (#186b62)
                D 6 (#161503)
                L 6 (#349a82)
                D 7 (#130743)
                L 3 (#302922)
                U 7 (#5eb933)
                L 4 (#16d232)
                D 4 (#38db13)
                L 7 (#0c7572)
                D 2 (#088f03)
                L 3 (#39d402)
                D 6 (#103c33)
                R 5 (#088bc2)
                D 5 (#4ca4f3)
                L 5 (#0a4d72)
                D 3 (#164d23)
                L 3 (#4ea5c2)
                U 4 (#3ed063)
                L 8 (#14f962)
                U 6 (#0d5d23)
                R 5 (#2b7da2)
                U 7 (#4cca33)
                L 5 (#1b1b92)
                U 3 (#689203)
                L 5 (#405a30)
                D 7 (#32c073)
                L 8 (#405a32)
                D 2 (#4e3c13)
                L 11 (#3d24a2)
                D 4 (#334ec3)
                L 2 (#1f55e2)
                D 5 (#5dca61)
                L 5 (#3d3622)
                U 6 (#5dca63)
                L 3 (#1d6682)
                D 6 (#08e9f3)
                L 5 (#14d5b2)
                D 5 (#08e9f1)
                L 3 (#6625a2)
                D 3 (#346e63)
                L 8 (#22b872)
                D 6 (#14c211)
                R 7 (#2219b0)
                D 7 (#394bf1)
                R 5 (#4d7d60)
                U 7 (#228751)
                R 6 (#2b6c50)
                D 5 (#30a991)
                R 7 (#45fac0)
                U 3 (#25b831)
                R 3 (#535960)
                U 8 (#48a8e1)
                R 7 (#2793a2)
                U 11 (#237a11)
                R 4 (#32c372)
                D 9 (#2a5653)
                R 4 (#070242)
                D 3 (#15b7f3)
                R 5 (#070240)
                D 10 (#37a9d3)
                R 6 (#1c2d42)
                U 10 (#23eb01)
                R 3 (#4c60a2)
                D 6 (#2678a1)
                R 4 (#4c60a0)
                D 4 (#2d5471)
                R 4 (#2cb782)
                D 3 (#3849f1)
                R 3 (#2fcd22)
                D 7 (#21e851)
                R 7 (#15e8b2)
                D 2 (#404ba1)
                R 3 (#15e8b0)
                D 8 (#186851)
                L 7 (#47f202)
                D 7 (#47a231)
                L 4 (#0e3cd2)
                D 4 (#2332c3)
                L 11 (#2e3422)
                D 6 (#0bad51)
                L 4 (#5af602)
                D 9 (#0bad53)
                L 4 (#21bba2)
                U 9 (#4a96a3)
                L 7 (#06dab2)
                U 4 (#67e973)
                L 10 (#011ed0)
                U 4 (#449971)
                L 7 (#5cc5c0)
                U 9 (#449973)
                L 9 (#53dbe0)
                U 4 (#484fa3)
                L 2 (#0b1fc2)
                U 5 (#41abb1)
                L 11 (#299252)
                U 4 (#310503)
                L 7 (#08f4f2)
                U 12 (#2f4c23)
                L 2 (#227db2)
                U 8 (#332e73)
                L 10 (#3b8d22)
                U 5 (#01dd73)
                L 5 (#1ce162)
                U 3 (#053f73)
                L 3 (#119a92)
                U 8 (#29f713)
                L 6 (#48ec42)
                D 8 (#363073)
                L 7 (#502b12)
                D 3 (#3ebd13)
                R 6 (#1c7512)
                D 6 (#27c1e3)
                R 10 (#187ac0)
                D 6 (#319253)
                R 5 (#1aac60)
                D 10 (#3e0a33)
                L 2 (#1adf30)
                D 5 (#147b71)
                L 3 (#555480)
                D 7 (#5b2111)
                L 7 (#00d2a0)
                U 7 (#10f163)
                L 9 (#273220)
                D 6 (#0d96c1)
                L 8 (#486b30)
                U 3 (#0d96c3)
                L 8 (#0b6cd0)
                U 7 (#10f161)
                L 6 (#2bd080)
                U 4 (#0d2f63)
                L 3 (#059010)
                U 7 (#1fa783)
                L 4 (#6de600)
                U 12 (#1fa781)
                L 7 (#05e470)
                U 3 (#2c9673)
                L 3 (#355ef0)
                U 6 (#0dddd3)
                R 9 (#423f90)
                U 3 (#1e9993)
                L 9 (#203672)
                U 7 (#617de3)
                R 8 (#363ae2)
                U 4 (#18e0d3)
                R 9 (#567150)
                U 8 (#0a1ec3)
                R 9 (#423f92)
                U 7 (#0c7353)
                L 12 (#1d13e0)
                U 6 (#34a503)
                R 12 (#36c650)
                U 4 (#503231)
                R 6 (#3fcd60)
                U 3 (#1178f1)
                R 6 (#4440a2)
                D 2 (#54b101)
                R 3 (#4440a0)
                D 11 (#3bb101)
                R 7 (#451730)
                D 11 (#2c9671)
                R 4 (#120090)
                D 4 (#1151a3)
                R 8 (#2b7da0)
                U 5 (#3639a3)
                L 4 (#3496a0)
                U 8 (#360c61)
                R 4 (#1135d0)
                U 7 (#1c2621)
                L 5 (#1532f0)
                U 8 (#29b063)
                L 8 (#6bcef0)
                U 7 (#29b061)
                L 2 (#0091b0)
                U 2 (#4b6031)
                L 9 (#255780)
                U 4 (#350441)
                L 5 (#154042)
                D 4 (#2fa351)
                L 9 (#06cc12)
                U 3 (#52a463)
                R 5 (#248972)
                U 7 (#3fbe03)
                R 5 (#21fe22)
                U 12 (#3fbe01)
                R 6 (#3c11c2)
                D 12 (#04d873)
                R 4 (#1f4f92)
                D 7 (#577cd1)
                R 6 (#09f782)
                U 7 (#1fb581)
                R 7 (#18e170)
                U 8 (#3883c1)
                R 5 (#0ec9a0)
                U 5 (#634a93)
                R 3 (#4a3e40)
                U 7 (#634a91)
                R 4 (#256ad0)
                U 4 (#2bd481)
                R 10 (#309890)
                U 5 (#1634b1)
                R 6 (#4e19d0)
                U 4 (#2f6a43)
                R 5 (#352aa2)
                D 9 (#4ae2e3)
                R 4 (#352aa0)
                D 3 (#073513)
                R 7 (#525d70)
                D 7 (#1929c3)
                R 10 (#3ae400)
                D 8 (#311ef3)
                R 3 (#078122)
                D 9 (#180443)
                L 4 (#042fc2)
                D 3 (#1a4371)
                L 5 (#266852)
                U 5 (#178891)
                L 8 (#4b05f2)
                D 5 (#178893)
                L 3 (#0cc4f2)
                D 4 (#1a4373)
                R 6 (#104642)
                D 4 (#362783)
                R 9 (#206000)
                D 3 (#6ea953)
                R 5 (#2bd220)
                D 8 (#060073)
                R 8 (#1b5420)
                D 4 (#279771)
                R 4 (#2d7662)
                D 9 (#46f751)
                R 7 (#2d7660)
                D 10 (#061b01)
                R 3 (#32a410)
                U 12 (#28ef03)
                R 3 (#1063a0)
                U 6 (#152583)
                L 4 (#571f30)
                U 6 (#0b19b3)
                R 4 (#0e4fe0)
                U 4 (#4fbf11)
                R 7 (#0379f0)
                U 4 (#4874e1)
                R 3 (#630bc2)
                D 8 (#0a46c1)
                R 5 (#0b7cd2)
                D 4 (#5c2dc1)
                R 3 (#15c350)
                U 12 (#31b781)
                R 5 (#58c540)
                U 6 (#2d39d1)
                R 7 (#0379f2)
                D 2 (#058b11)
                R 6 (#31ce00)
                D 5 (#1cd3b1)
                R 9 (#5087e0)
                U 5 (#3ee9d1)
                R 3 (#5087e2)
                D 9 (#257411)
                R 3 (#48a7b2)
                U 7 (#3ed791)
                R 8 (#614372)
                U 6 (#4fbf11)
                L 4 (#59f212)
                U 5 (#4fbf13)
                L 7 (#36c9d2)
                D 5 (#06dc01)
                L 6 (#4854f2)
                U 9 (#2c8471)
                R 4 (#1ceb70)
                U 7 (#4953b1)
                R 6 (#1bf890)
                D 7 (#60a641)
                R 7 (#1bf892)
                U 3 (#114ff1)
                R 4 (#2ac060)
                D 6 (#0dae41)
                R 6 (#5361f0)
                D 4 (#00f171)
                L 6 (#524080)
                D 4 (#2b4ec3)
                R 5 (#1dba40)
                D 7 (#2b4ec1)
                R 6 (#3f2d70)
                D 4 (#595893)
                R 3 (#2c3260)
                U 11 (#3aa063)
                R 8 (#36fa30)
                U 7 (#48eea3)
                L 9 (#39e1d0)
                U 5 (#0afa71)
                L 3 (#2657f0)
                U 10 (#3c6601)
                L 6 (#1e8fa0)
                U 8 (#3c2e91)
                L 4 (#40bad0)
                U 2 (#0f50d1)
                L 6 (#6184d0)
                U 2 (#4e1ac1)
                L 4 (#44e180)
                D 4 (#154dc3)
                L 5 (#2fb800)
                D 7 (#257ce1)
                L 5 (#2892f0)
                D 5 (#257ce3)
                L 9 (#1dc870)
                U 6 (#481dd3)
                L 7 (#157020)
                U 6 (#595891)
                L 6 (#22f720)
                D 13 (#4fa6e3)
                L 2 (#1ebab0)
                U 13 (#4f5361)
                L 7 (#3d92d0)
                U 4 (#4f5363)
                L 5 (#254680)
                U 9 (#203463)
                R 9 (#4bc310)
                U 2 (#4ac9a3)
                R 4 (#0eb840)
                U 12 (#238933)
                R 6 (#5983c0)
                D 11 (#3bad63)
                R 5 (#0a9a90)
                D 3 (#5f3691)
                R 10 (#1a75b0)
                U 2 (#353433)
                R 10 (#4d7910)
                U 5 (#0f8c63)
                R 4 (#0f01a0)
                U 5 (#41d983)
                R 12 (#567c70)
                U 7 (#235bf3)
                L 6 (#3d3260)
                U 7 (#3670c3)
                R 8 (#0ae380)
                U 11 (#39f0f3)
                L 8 (#5a3b50)
                U 7 (#3ef7f3)
                R 6 (#1f8b10)
                U 9 (#0af2c3)
                R 7 (#352e02)
                U 4 (#4ac4f3)
                L 4 (#4f7be2)
                U 8 (#5290f3)
                R 4 (#5dfcb0)
                U 5 (#52a673)
                R 5 (#209ad0)
                D 5 (#4aec43)
                R 4 (#10d190)
                U 7 (#10af81)
                R 3 (#466070)
                U 7 (#40f621)
                R 5 (#466072)
                U 6 (#393501)
                R 5 (#208170)
                U 5 (#5f05d1)
                R 3 (#3543d0)
                D 8 (#4838b3)
                R 4 (#0578c0)
                D 7 (#700eb3)
                L 4 (#3ee0f0)
                D 3 (#362cd1)
                R 3 (#5b4aa0)
                D 7 (#3acda1)
                R 9 (#5b4aa2)
                D 10 (#404441)
                R 10 (#2587c0)
                D 8 (#0708b1)
                R 7 (#208490)
                D 3 (#1c1d31)
                R 5 (#125af2)
                U 12 (#35d991)
                R 4 (#34d352)
                D 12 (#33bc31)
                R 6 (#154a20)
                D 6 (#47f8b1)
                R 5 (#154a22)
                U 6 (#04ad41)
                R 11 (#293322)
                U 8 (#1f1b33)
                R 10 (#25e712)
                U 3 (#243993)
                R 4 (#25e710)
                U 2 (#3d0d63)
                R 5 (#358112)
                U 8 (#1c2051)
                R 5 (#3a48d2)
                U 5 (#365811)
                R 5 (#0c80c0)
                D 10 (#1acc61)
                R 4 (#54dcc0)
                D 3 (#350a11)
                R 8 (#219790)
                D 10 (#49ee81)
                R 6 (#3ab6e0)
                D 7 (#34ec81)
                R 5 (#6ec4d0)
                D 3 (#102551)
                R 9 (#020e20)
                D 2 (#3dd911)
                R 5 (#55d840)
                D 3 (#2e4331)
                R 5 (#254262)
                D 9 (#54ac01)
                R 10 (#576822)
                D 5 (#2b7e21)
                L 12 (#6d7532)
                D 3 (#3bbd91)
                L 3 (#085512)
                D 5 (#1801d1)
                R 6 (#495022)
                D 6 (#48c991)
                L 12 (#2c6252)
                D 3 (#60cb63)
                L 6 (#3a6cf2)
                D 3 (#160ad3)
                L 6 (#3a53d2)
                D 11 (#184dd3)
                L 3 (#2551e2)
                D 3 (#184dd1)
                L 11 (#4d0072)
                D 6 (#160ad1)
                L 4 (#445b42)
                D 6 (#52c611)
                R 6 (#337af2)
                D 2 (#28d391)
                R 8 (#1e6790)
                D 10 (#1e46d1)
                L 10 (#422400)
                D 3 (#64ab01)
                R 4 (#1607d0)
                D 6 (#04f111)
                R 7 (#138d50)
                U 6 (#252281)
                R 9 (#3b06a2)
                D 6 (#3c3901)
                R 4 (#30b282)
                D 4 (#4568e1)
                L 8 (#385bc0)
                D 7 (#157261)
                L 4 (#4e9b22)
                D 8 (#689a31)
                L 12 (#4e9b20)
                D 3 (#290681)
                R 10 (#06ddf0)
                D 8 (#132223)
                R 9 (#686f60)
                D 7 (#4048d3)
                R 4 (#342060)
                D 3 (#204d51)
                R 5 (#07e040)
                D 4 (#204d53)
                L 6 (#3a7bb0)
                D 6 (#37d083)
                L 10 (#1f3d80)
                D 3 (#1d8fd3)
                L 5 (#16b082)
                D 9 (#119673)
                L 11 (#1d1772)
                D 3 (#2a2723)
                R 12 (#2b9662)
                D 8 (#2a2721)
                R 6 (#302fa2)
                U 8 (#43b103)
                R 11 (#0a3500)
                D 5 (#354b03)
                R 13 (#5eaea0)
                D 4 (#354b01)
                L 3 (#0ff9d0)
                D 7 (#327d63)
                L 3 (#16b080)
                D 3 (#05de43)
                L 3 (#381a00)
                D 5 (#4040e3)
                R 11 (#1a01b2)
                D 6 (#072c11)
                L 11 (#5c23c2)
                D 3 (#072c13)
                L 2 (#25cd12)
                D 5 (#362963)
                L 7 (#122ae0)
                U 8 (#2f98e3)
                L 3 (#4ac460)
                U 8 (#2f98e1)
                L 2 (#3f0340)
                U 6 (#28e1c3)
                L 6 (#490c50)
                U 5 (#2cace1)
                L 6 (#2ea3d0)
                D 4 (#3d8f31)
                L 6 (#45cf70)
                U 4 (#21dc11)
                L 5 (#32f7f2)
                U 5 (#0ad623)
                L 4 (#0e0272)
                U 6 (#0ad621)
                L 8 (#3378e2)
                D 7 (#12c121)
                L 11 (#1805f0)
                D 4 (#0edd81)
                L 11 (#61ecf0)
                U 7 (#1355b1)
                L 3 (#104e80)
                U 7 (#3c6bf1)
                L 4 (#084972)
                U 11 (#15dd41)
                R 5 (#685922)
                U 3 (#3b0f11)
                L 6 (#0ec442)
                U 7 (#36c961)
                R 6 (#6f14b0)
                U 5 (#3298b1)
                R 8 (#105220)
                U 3 (#310571)
                R 4 (#2b1702)
                U 9 (#260fa3)
                R 8 (#455922)
                U 3 (#260fa1)
                R 3 (#19d142)
                U 8 (#17ea11)
                R 6 (#38ecd2)
                U 5 (#1a1931)
                L 5 (#464de2)
                U 3 (#4c3b91)
                L 4 (#1b16c2)
                U 9 (#6654c3)
                L 8 (#1e7dd2)
                U 5 (#2a1f91)
                L 3 (#2a7b62)
                U 4 (#26c251)
                L 4 (#250742)
                U 5 (#275c11)
                L 10 (#13c962)
                U 5 (#275c13)
                L 4 (#37b4a2)
                U 3 (#3be851)
                L 5 (#0401a0)
                U 9 (#29b7e1)
                L 3 (#044032)
                U 5 (#12e441)
                L 13 (#380b60)
                U 3 (#38e951)
                L 3 (#28c040)
                U 4 (#2b40e1)
                L 8 (#60cba2)
                U 7 (#059311)
                L 6 (#044030)
                U 11 (#2c1a41)
                L 4 (#0401a2)
                D 7 (#0d1291)
                L 5 (#11c4a2)
                U 10 (#0d1e61)
                L 4 (#28e382)
                D 10 (#32d681)
                L 5 (#4a6022)
                D 5 (#31a741)
                R 11 (#22f822)
                D 4 (#647dc3)
                R 3 (#294f62)
                D 4 (#18f561)
                R 4 (#3762b2)
                D 5 (#4ebb03)
                L 6 (#4c2a52)
                D 9 (#4ebb01)
                R 6 (#5c0d82)
                D 5 (#18f563)
                L 4 (#16a472)
                D 3 (#0e1271)
                R 13 (#000c12)
                D 6 (#465a51)
                L 13 (#0bf4b2)
                D 6 (#0ffe93)
                L 2 (#3b2c42)
                D 4 (#0ffe91)
                R 9 (#303472)
                D 5 (#465a53)
                R 6 (#00a1d2)
                D 5 (#3f5ec1)
                R 12 (#11b762)
                D 5 (#1fec81)
                R 8 (#04d602)
                D 2 (#346b31)
                R 3 (#6da912)
                U 5 (#2afbc1)
                R 5 (#45c912)
                D 5 (#1b0d61)
                R 6 (#3915f2)
                D 5 (#1b0d63)
                L 5 (#23bef2)
                D 13 (#06d751)
                L 5 (#671192)
                U 5 (#3a9191)
                L 7 (#171f52)
                U 5 (#582ea1)
                L 4 (#5084e2)
                U 7 (#1bc721)
                L 6 (#46b262)
                D 4 (#3b23b3)
                L 4 (#565362)
                D 5 (#2c5f73)
                L 5 (#2c14a2)
                D 8 (#240191)
                L 5 (#638662)
                D 7 (#438191)
                L 8 (#121f12)
                D 6 (#088063)
                L 5 (#2b2ed2)
                D 9 (#340df3)
                L 11 (#30dc52)
                D 6 (#32cbf3)
                L 10 (#451942)
                D 8 (#1cf853)
                L 10 (#4f1e12)
                D 3 (#32afd3)
                L 4 (#4f1e10)
                D 4 (#43d723)
                R 12 (#209752)
                D 6 (#4b2143)
                L 12 (#65b090)
                D 4 (#29ebd3)
                L 4 (#02cb22)
                U 9 (#380263)
                L 5 (#643ff2)
                U 6 (#4844d3)
                L 7 (#376762)
                U 5 (#11c443)
                L 2 (#487de2)
                U 7 (#6e1383)
                L 7 (#184542)
                U 7 (#12f1d3)
                """;
        //endregion
    }

    private static class DigPlanItem {
        Direction direction;
        final int cubeCount;
        final String colourCode;

        public DigPlanItem(String planItemStr) {
            String[] planItemArray = planItemStr.split(" ");
            switch (planItemArray[0]) {
                case "R" -> this.direction = Direction.EAST;
                case "D" -> this.direction = Direction.SOUTH;
                case "L" -> this.direction = Direction.WEST;
                case "U" -> this.direction = Direction.NORTH;
            }
            this.cubeCount = Integer.parseInt(planItemArray[1]);
            this.colourCode = planItemArray[2];
        }

        public int getCubeCount() {
            return cubeCount;
        }

        public String getColourCode() {
            return colourCode;
        }

        public Direction getDirection() {
            return direction;
        }
    }

    private static class DigCube {
        final Coordinate coordinate;
        final String colourCode;
        final int row;
        final int column;

        public DigCube(Coordinate coordinate, String colourCode) {
            this.coordinate = coordinate;
            this.colourCode = colourCode;
            this.row = coordinate.getRow();
            this.column = coordinate.getColumn();
        }
    }

    private static class DigPerimeter extends Grid<DigCube> {
        final int firstRow;
        final int firstColumn;
        final int lastRow;
        final int lastColumn;

        DigPerimeter(List<DigPlanItem> digPlanItems) {

            Coordinate coordinate = new Coordinate(0, 0);
            for (DigPlanItem digPlanItem : digPlanItems) {
                String colourCode = digPlanItem.getColourCode();
                Direction direction = digPlanItem.getDirection();
                int cubeCount = digPlanItem.getCubeCount();

                for (int i = 0; i < cubeCount; i++) {
                    DigCube digCube = new DigCube(coordinate, colourCode);
                    this.put(coordinate.getRow(), coordinate.getColumn(), digCube);

                    coordinate = coordinate.move(direction);
                }
            }

            this.firstRow = this.keySet().stream().map(Coordinate::getRow).reduce(0, Integer::min);
            this.firstColumn = this.keySet().stream().map(Coordinate::getColumn).reduce(0, Integer::min);
            this.lastRow = Math.toIntExact(this.height) - 1;
            this.lastColumn = Math.toIntExact(this.width) - 1;
        }

        public int getFirstRow() {
            return firstRow;
        }

        public int getFirstColumn() {
            return firstColumn;
        }

        public int getLastRow() {
            return lastRow;
        }

        public int getLastColumn() {
            return lastColumn;
        }
    }

    //TODO: Determine if this, from Day 10, can be used to make part 1 work with the real data and not just the demo data.
    private static boolean isInsideBoundingBox(DigPerimeter digPerimeter, Coordinate coordinate) {

        int minRow = digPerimeter.getFirstRow();
        int minColumn = digPerimeter.getFirstColumn();
        int maxRow = digPerimeter.getLastRow();
        int maxColumn = digPerimeter.getLastColumn();

        boolean isValidRow = coordinate.getRow() >= minRow && coordinate.getRow() <= maxRow;
        boolean isValidColumn = coordinate.getColumn() >= minColumn && coordinate.getColumn() <= maxColumn;

        return isValidRow && isValidColumn;
    }

    private static boolean isInsidePerimeter(DigPerimeter digPerimeter, Coordinate coordinate) {

        if (digPerimeter.containsKey(coordinate)) {
            return false;
        }

        boolean toTheLeft = !digPerimeter.keySet().stream()
                .filter(coord -> coord.getRow() == coordinate.getRow())
                .filter(coord -> coord.getColumn() < coordinate.getColumn())
                .toList()
                .isEmpty();

        if (!toTheLeft) {
            return false;
        } else {
            boolean toTheRight = !digPerimeter.keySet().stream()
                    .filter(coord -> coord.getRow() == coordinate.getRow())
                    .filter(coord -> coord.getColumn() > coordinate.getColumn())
                    .toList()
                    .isEmpty();

            if (!toTheRight) {
                return false;
            } else {
                boolean toTheTop = !digPerimeter.keySet().stream()
                        .filter(coord -> coord.getColumn() == coordinate.getColumn())
                        .filter(coord -> coord.getRow() < coordinate.getRow())
                        .toList()
                        .isEmpty();

                if (!toTheTop) {
                    return false;
                } else {
                    return !digPerimeter.keySet().stream()
                            .filter(coord -> coord.getColumn() == coordinate.getColumn())
                            .filter(coord -> coord.getRow() > coordinate.getRow())
                            .toList()
                            .isEmpty();
                }
            }
        }
    }

    public static void main(int part, boolean isDemo) {
        long startTime = System.nanoTime();
        int expectedResult;
        int actualResult;

        String dataStr = isDemo ? DEMO_DATA : DATA;
        List<DigPlanItem> digPlanItems = Arrays.stream(dataStr.split("\n"))
                .map(DigPlanItem::new)
                .toList();

        DigPerimeter digPerimeter = new DigPerimeter(digPlanItems);

        if (part == 1) {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_ONE : EXPECTED_RESULT_PART_ONE;

            int minRow = digPerimeter.getFirstRow();
            int minColumn = digPerimeter.getFirstColumn();
            int maxRow = digPerimeter.getLastRow();
            int maxColumn = digPerimeter.getLastColumn();

            List<Coordinate> internalCoordinates = new ArrayList<>();
            for (int row = minRow; row <= maxRow; row++) {
                for (int column = minColumn; column <= maxColumn; column++) {
                    Coordinate coordinate = new Coordinate(row, column);
                    if (isInsidePerimeter(digPerimeter, coordinate)) {
                        internalCoordinates.add(coordinate);
                    }

                }
            }

            actualResult = digPerimeter.size() + internalCoordinates.size();
        } else {
            expectedResult = isDemo ? EXPECTED_RESULT_DEMO_PART_TWO : EXPECTED_RESULT_PART_TWO;
            actualResult = 0;
        }

        Utils.publishResult(startTime, expectedResult, actualResult);
    }
}
