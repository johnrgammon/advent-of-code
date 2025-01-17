package ca.gammon.advent2023.days;

import static ca.gammon.advent2023.days.Day7.HandType.FIVE_OF_A_KIND;
import static ca.gammon.advent2023.days.Day7.HandType.FOUR_OF_A_KIND;
import static ca.gammon.advent2023.days.Day7.HandType.FULL_HOUSE;
import static ca.gammon.advent2023.days.Day7.HandType.HIGH_CARD;
import static ca.gammon.advent2023.days.Day7.HandType.ONE_PAIR;
import static ca.gammon.advent2023.days.Day7.HandType.THREE_OF_A_KIND;
import static ca.gammon.advent2023.days.Day7.HandType.TWO_PAIR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import ca.gammon.advent2023.days.utils.Utils;

public class Day7 {

    private static final int EXPECTED_WINNINGS_PART_ONE = 251216224;
    private static final int EXPECTED_WINNINGS_DEMO_PART_ONE = 6440;
    private static final int EXPECTED_WINNINGS_PART_TWO = 250825971;
    private static final int EXPECTED_WINNINGS_DEMO_PART_TWO = 5905;

    private static final String HAND_TYPE_DATA;
    private static final String HAND_TYPE_DATA_DEMO;
    static {
        HAND_TYPE_DATA_DEMO = """
                32T3K 765
                T55J5 684
                KK677 28
                KTJJT 220
                QQQJA 483""";
        HAND_TYPE_DATA = """
                3Q373 470
                K53JT 351
                A9JK9 856
                2T333 515
                867T4 541
                58K22 253
                5JA6J 994
                K4A4K 865
                94377 519
                92J2Q 901
                J7676 389
                2KK36 938
                JQ2KK 987
                Q7A82 509
                TTTA5 243
                72J27 502
                AKKKA 387
                23222 674
                55335 161
                AA655 73
                QKKQA 686
                5J2T5 680
                666AT 385
                ATA3A 761
                TTT8J 364
                98A2T 282
                59A44 260
                6T9QJ 130
                T7TKQ 721
                9274T 656
                T9AJ4 182
                A2222 259
                TQKJ7 67
                4J844 560
                AAATA 636
                9J36J 546
                QJQQQ 119
                249TT 295
                877J7 221
                4KA23 116
                929Q2 929
                99JQQ 445
                Q9QJ8 432
                4Q7TJ 963
                4J2J8 783
                5J657 257
                88788 183
                KKK7K 909
                KK396 637
                J45AT 117
                84448 806
                A2698 820
                A9AA5 320
                A3934 19
                Q45Q5 392
                78686 254
                A55A5 701
                98A4K 655
                5AJ88 95
                AK3KK 55
                773KK 33
                J9J9J 356
                2KAKK 563
                655K5 287
                55T56 475
                54444 308
                5J5QA 811
                QKA76 792
                2K288 42
                JTKA2 694
                88668 889
                8TQ64 212
                23QQ2 261
                8K8K5 365
                JKKAA 234
                3J894 880
                667Q2 817
                888T4 599
                T5Q83 315
                3QK29 670
                JK4KK 322
                75T8T 144
                AAA7K 291
                7T333 495
                A49K4 448
                JJ3J3 251
                5999J 278
                38438 890
                6K3Q9 122
                TTT99 93
                94775 240
                K8QJ3 369
                59674 107
                267J4 355
                QTQTQ 187
                46J44 961
                Q7AK2 580
                69969 609
                876A5 474
                A3866 642
                Q3QQA 526
                JAA88 425
                35Q9K 986
                QQTQJ 550
                54535 357
                2J222 639
                QK5J7 743
                222Q2 191
                T4T44 855
                QTQQ2 338
                JA7A2 300
                AAAJ2 508
                4J455 23
                A77AA 339
                5KK25 899
                252J2 737
                7QJ76 888
                K46QK 795
                A4T3J 640
                8A8K8 176
                AA5AA 41
                J2Q24 958
                58855 632
                333J3 610
                74474 807
                K3AAA 78
                JJ222 765
                44224 688
                76733 361
                5845K 181
                TT4T2 974
                23J33 102
                QQJ6Q 34
                Q9AQQ 150
                22747 717
                KAA5A 121
                7AA7J 286
                J786T 780
                3K8TT 32
                A6KAA 25
                9A362 166
                A29Q2 499
                6688K 57
                KKQKA 947
                999JJ 975
                6A666 927
                TTT3K 658
                JQQ7T 467
                8A247 934
                7JK8J 712
                KK39K 612
                6666Q 17
                77Q37 868
                A66AJ 169
                KKKKJ 535
                7Q7JJ 591
                K9848 646
                46888 931
                A86A8 326
                743TA 527
                QKK67 977
                KKJ6K 471
                AAJ7A 571
                55292 330
                3A222 390
                6453J 109
                JKKKA 403
                4J9T5 21
                K7A5K 486
                6TT34 793
                3479Q 60
                44624 443
                6KK3K 164
                4447J 112
                QQ27Q 452
                JAK65 522
                Q5Q5Q 354
                J5Q6A 540
                68999 711
                3QJ4Q 155
                A6AAA 314
                222KK 767
                55T9A 373
                T5T57 913
                TTTTA 794
                8J258 190
                3Q87J 489
                AAQAQ 950
                Q4JJ4 588
                6T64T 9
                636K5 228
                AA222 248
                7KJ35 616
                26646 507
                44A4J 829
                23323 904
                TA265 135
                JJQT9 136
                KATJ8 644
                J2862 713
                4JQ28 152
                Q3K2T 56
                6Q232 812
                9A622 885
                KJKKJ 773
                3859T 374
                99333 101
                TKTTK 205
                3333A 35
                5A4AQ 548
                T4944 967
                43353 813
                423JK 869
                T99T7 219
                3AAAA 394
                T65Q6 740
                J9243 352
                6656J 496
                444K4 574
                Q8T6K 98
                AQ55K 917
                7QQJ7 605
                TJ5TT 615
                68JQ5 852
                7A3J2 745
                369AK 217
                57755 343
                A8844 61
                Q76QQ 698
                36333 774
                237Q7 12
                KT694 283
                88286 154
                4T552 990
                Q9J54 759
                K6697 350
                A3A33 937
                AA9AA 246
                QQ7KK 748
                TT4A6 380
                4J992 845
                A66AA 573
                5QQ2Q 28
                86J37 214
                2A443 362
                Q9QKK 654
                44434 964
                77773 74
                44355 235
                9QK75 863
                3AJ33 766
                T2TJT 53
                44494 860
                883A9 309
                QQ59Q 360
                A88A8 584
                A83A4 395
                7J9TA 299
                55955 833
                QJK35 188
                4656A 514
                8KTQ4 554
                56J47 985
                J272J 184
                9J999 671
                72295 31
                53555 264
                4A537 750
                7A4JJ 875
                QK834 463
                88KKA 570
                QQ9JK 906
                6Q646 858
                53336 359
                QJ8QJ 587
                424TJ 607
                KTKK6 285
                Q5AQQ 84
                899A8 542
                K8TKK 250
                4JA4A 705
                6J75J 896
                888K8 312
                5583J 660
                6T335 614
                Q757T 945
                3AKKA 629
                KK946 825
                3393Q 406
                7J288 367
                49999 907
                99729 837
                66265 384
                K47JQ 263
                2AJ4K 566
                TTKKA 186
                8J877 418
                56844 666
                JTTTT 531
                6TJ94 26
                JQ5J5 344
                7J772 85
                33747 510
                KJA2A 729
                333TQ 578
                JQAT4 955
                75766 834
                QQ6QQ 980
                32K33 306
                TTTJ6 231
                2QTTT 213
                999AQ 608
                33353 202
                Q8Q8Q 756
                KK555 490
                746K8 691
                98A75 853
                JQ858 816
                KK9KK 536
                9T999 933
                TA8A8 39
                8558J 919
                J8KQK 15
                73Q2K 503
                2K2KJ 585
                JK9K5 903
                TJ7T7 545
                J723T 453
                KT895 583
                TTTKT 123
                39535 393
                K4K88 893
                43KK6 821
                6AA8J 504
                972TA 597
                7J736 592
                536A8 63
                J23Q3 402
                83K9Q 405
                28337 396
                9T558 776
                7AA78 946
                QTT24 441
                26QT3 401
                AJQAQ 953
                6T6AJ 304
                JK5KK 162
                2JJ2J 484
                73773 6
                8J864 630
                78782 557
                6TJ52 424
                487JQ 830
                8J698 82
                AJAAA 866
                228J8 204
                622J4 823
                6J2QJ 79
                22K22 232
                A86A6 444
                83K8Q 965
                JK586 944
                JA848 465
                8888A 45
                2T28T 803
                73J73 450
                27272 857
                7A777 544
                A998J 679
                46466 458
                KJ233 192
                4453J 378
                TQQJT 451
                A3K5J 512
                888Q6 968
                6AA66 841
                QQ222 883
                3K5A3 233
                JA4AA 379
                62288 778
                44456 51
                4A644 862
                55647 981
                A77A7 327
                KQ3A3 687
                QQ7Q7 976
                T4A2T 293
                46226 628
                Q59Q5 348
                36AJ2 984
                54433 532
                JJQ24 831
                7455J 381
                78845 218
                72AQ5 276
                52T22 618
                99399 68
                36AA3 118
                69669 839
                33Q5J 785
                TJ766 626
                T28J4 556
                T883Q 142
                73928 932
                TJ884 494
                AJ36Q 485
                9Q999 324
                T725Q 267
                22622 88
                92364 685
                97792 789
                J673K 620
                99943 625
                77JJ7 921
                8828Q 572
                7777J 353
                TAAJT 926
                99T29 787
                AQ4J4 520
                9J559 5
                J3T94 706
                K9KK9 918
                KK252 439
                79925 97
                TT47T 497
                848T4 126
                6TTT6 426
                K8825 925
                888J8 62
                K5K85 972
                AAKKA 449
                J5555 663
                89J99 27
                4Q444 922
                A9A99 397
                5A555 163
                8J3K8 662
                29989 645
                95222 751
                Q4Q4Q 193
                555K5 982
                74AJ8 735
                KKQ6K 668
                7676Q 916
                A28T7 784
                K57KK 388
                A4J4J 115
                2K442 229
                33TT3 956
                TT6TA 741
                TT782 131
                TJ5JQ 238
                TQ7T7 415
                29KQQ 598
                A8AAT 676
                46A28 650
                6698K 696
                6J969 158
                6694A 271
                QTQ5T 498
                Q2QQQ 613
                2A7QJ 210
                6T466 683
                QJ575 799
                Q63Q3 822
                Q9J28 848
                TTTT7 7
                KA9AA 854
                K2JJT 859
                28228 179
                K29KJ 429
                JJ8JJ 222
                A99A5 457
                QQTTT 983
                43KK2 134
                984JJ 1
                97J97 892
                2AAA8 689
                9Q77J 819
                98TJK 517
                56KA5 71
                7J66J 294
                55855 335
                TTT8T 596
                33663 407
                7J774 911
                3JQ55 564
                QQQ7Q 329
                85668 120
                KK333 301
                Q3553 867
                7K557 988
                Q8488 400
                96J99 643
                29224 714
                T8ATA 346
                99J93 462
                K83J3 697
                3KQJ4 653
                5KJQ2 824
                4JJ3A 897
                56248 727
                JJ666 702
                355T5 245
                45KAK 553
                88AAA 236
                K55J5 581
                AJJ49 65
                A8Q3J 197
                AAAKQ 529
                55976 707
                77776 930
                A9995 227
                K7J7K 912
                29K99 244
                4Q464 80
                22972 195
                666JQ 189
                26278 241
                79288 601
                5454A 427
                JTAKQ 138
                88833 549
                84J54 215
                AATAK 114
                58959 145
                8ATTJ 431
                QJ26K 265
                A3JA3 81
                T8T4J 242
                4T3A3 37
                44777 871
                JQ542 561
                A97Q9 693
                A76T4 412
                28T4A 481
                T485A 861
                78286 303
                K6776 52
                T8TKQ 754
                KJ938 957
                Q444Q 840
                25A89 455
                58588 673
                K8858 634
                K8KJ8 170
                K6KKK 851
                53353 140
                2T2K6 593
                QQAQA 48
                TT6J6 305
                922J2 920
                AA22K 22
                J555Q 725
                TTAAT 781
                2244J 171
                A95K7 436
                2J299 651
                78AAA 321
                2KKK2 690
                2J565 649
                77J5K 77
                T999T 762
                J4445 879
                J888Q 873
                J884J 1000
                98898 413
                29299 442
                QKQ3Q 678
                A652A 993
                55272 898
                KA5K5 664
                8KJAQ 147
                Q56QJ 734
                K77J3 739
                J7677 661
                T5569 206
                2AA2J 757
                TJTJT 342
                T2TTT 682
                88J82 363
                9KK78 700
                AT4T4 730
                86968 736
                99995 207
                7A3J4 172
                8KK87 991
                TKKJJ 16
                J7532 165
                J9888 996
                9AJTA 92
                43T78 310
                93J77 317
                J4424 805
                43J86 703
                952TJ 127
                54363 146
                8T2J7 435
                88848 832
                TAAAQ 791
                8228T 256
                699QT 815
                KQQQK 30
                T82J3 49
                36222 4
                AK288 86
                6792A 194
                2Q22T 667
                J3545 270
                9QJK4 280
                QQ22Q 371
                K9247 446
                A422A 943
                765K3 469
                9J5A6 874
                4A4QA 775
                8Q8Q8 124
                T8Q62 850
                8Q8Q5 747
                6KT88 738
                82888 722
                3TQQQ 349
                2J3TT 539
                7TQ59 72
                8998T 733
                JQJT3 414
                82428 589
                92856 423
                89833 54
                79T4A 523
                95839 979
                TJJT5 936
                33839 29
                37433 511
                TKJ2T 681
                JT973 633
                Q8Q8J 971
                Q5555 440
                K8886 24
                Q75A4 43
                3AQ33 298
                T446T 719
                577J5 753
                KJTKT 167
                52528 8
                25222 641
                QJ2T8 621
                A8JQJ 316
                7T7A7 772
                KQQTQ 141
                83734 758
                6Q66Q 434
                QJ6Q2 638
                AAA7A 196
                57555 203
                4QJQ4 216
                82J5Q 763
                A73A3 940
                46AJQ 559
                T4936 103
                89Q29 262
                7872T 466
                A5J58 274
                AAAQA 290
                37399 844
                A759Q 328
                TJ9TT 273
                77887 268
                64A2J 978
                6T76Q 110
                QQ467 742
                9J7Q5 422
                4K6Q3 91
                336K3 826
                6K63Q 382
                23552 105
                243AA 505
                Q887J 764
                2J92A 749
                68A3Q 416
                7T8T8 199
                225J5 404
                24774 153
                T7T77 870
                3J393 347
                Q5Q8J 128
                7Q777 796
                QKKKK 617
                86686 108
                44KT4 75
                56858 622
                5AJK4 960
                3245Q 311
                36536 370
                6644Q 11
                JKAAA 104
                T2426 579
                J3399 83
                33933 602
                A299J 777
                26J22 224
                8666J 594
                J3J38 669
                3AJ84 239
                52Q8T 877
                QJQQK 779
                QJ6T8 797
                43339 399
                9AJ74 476
                T499Q 13
                2TAKA 928
                T3K78 809
                2923K 201
                J8858 798
                T8888 818
                98399 168
                8T266 555
                23A57 652
                3KKK3 113
                22T22 460
                AJJAA 106
                7QKA8 464
                QQ8TQ 368
                99A99 341
                62Q5A 223
                J9T47 568
                Q7388 894
                69662 744
                96J2T 323
                2A779 89
                Q2874 269
                77797 948
                5359A 864
                AJ9K4 482
                Q3Q44 567
                JT958 665
                A38J3 438
                J4666 296
                428J5 501
                38K8K 410
                6T979 709
                3533T 284
                2285A 910
                933T6 882
                K6666 786
                56Q32 992
                5J752 198
                AJJQJ 600
                KK5KK 3
                38T94 180
                67QTJ 69
                J6636 200
                229J8 493
                QJTQ3 247
                5KQ5K 149
                654AT 528
                T84T4 878
                Q23QQ 905
                QQQ74 325
                4JTQQ 408
                T4T78 340
                3T5T5 551
                3J3J3 391
                88844 631
                TTT9T 810
                57775 935
                A777J 604
                4555T 2
                A5J8T 942
                22AJJ 506
                22323 997
                A3323 624
                84442 516
                2T992 76
                J7T76 828
                J8552 995
                KTKK3 886
                33853 782
                53233 129
                44J4J 372
                KQQT2 480
                K5649 576
                3J63K 760
                2J666 827
                6JT68 318
                45Q64 849
                49884 801
                9KTTK 902
                66737 952
                5Q8J4 708
                57Q92 724
                627J7 331
                K22J6 473
                4T444 923
                J4444 552
                78J8J 491
                4K328 477
                4AA3A 808
                7A34Q 437
                AQ888 428
                8TA99 768
                AA9A9 143
                9K5AT 211
                77722 40
                TQTTT 941
                Q2A4K 492
                236T9 677
                J8686 746
                J585K 64
                TJKQT 582
                T3TTJ 157
                22266 38
                KT5TK 692
                QJQQJ 908
                4K623 279
                7JJJ7 173
                64A66 524
                J7AJ8 417
                K43Q3 769
                T662T 148
                54J2J 488
                K6485 44
                J6666 970
                J26J9 209
                8337Q 334
                A27A4 319
                A7T78 132
                K777A 842
                KKK4K 249
                99K9K 430
                8745K 151
                Q2QTJ 433
                QKQ76 525
                25Q2Q 533
                5QK6J 800
                77T77 558
                5K323 939
                33KK4 590
                K8A52 111
                ATQJ6 569
                ATK89 50
                4433A 836
                3KKKQ 891
                46TK4 538
                5249Q 447
                2462K 675
                8JT6T 336
                466J7 459
                7K7KK 838
                544Q4 959
                58773 483
                823J5 999
                2TKA5 752
                AKJ58 386
                7T833 99
                KQ88Q 659
                KJ3A4 790
                K9595 307
                3K3J4 534
                3838T 220
                32522 720
                366Q3 500
                J8868 989
                T7KTK 376
                JQQQA 14
                42555 409
                5T5T5 648
                4KK4Q 383
                K7777 884
                736A3 543
                343Q3 366
                TJ856 237
                JJ555 46
                K5QQQ 275
                QAQQ7 672
                8KTJQ 731
                QQQ3Q 47
                J257Q 575
                6Q3QQ 87
                QKK43 924
                TTATJ 998
                K44K5 771
                37895 881
                48823 521
                KKK74 456
                99J79 139
                J4542 635
                AJ96K 954
                K8937 411
                5J355 895
                96967 185
                333T3 562
                TAJ77 755
                J38A8 487
                AAT79 313
                A6Q73 398
                386JA 10
                J35Q8 623
                27758 969
                3K3KJ 966
                7928Q 627
                AAA4A 59
                2AA52 478
                848J8 133
                55225 843
                TTQT9 619
                3Q79K 255
                27AK8 58
                TKTKK 272
                KTTAT 333
                55666 802
                47748 454
                Q3T56 699
                63T3T 302
                8754Q 723
                4J86T 358
                8TTQ9 226
                5523J 125
                88588 606
                58AAA 208
                TK273 137
                4Q7KQ 160
                29292 577
                Q333Q 611
                22TT2 716
                A55AA 258
                64822 468
                22228 847
                2242T 788
                JA26Q 472
                8JAAA 174
                K5Q8A 175
                Q5AT4 732
                J4434 530
                76666 337
                9998Q 547
                9T2J9 100
                3Q982 90
                7727Q 281
                92944 973
                K2597 814
                K444K 36
                26KJ6 292
                34K38 962
                5A378 225
                636J9 375
                TTTT4 420
                A66TA 461
                69QK5 345
                76293 479
                66966 804
                A3383 297
                T82TT 657
                2QJJQ 156
                K2JK3 94
                K6K66 252
                K58QJ 876
                6J979 513
                92647 718
                QQ9Q9 586
                862Q5 20
                5465K 915
                KQ44Q 595
                T66T6 288
                TJT7T 710
                5JT8K 159
                KJTK3 603
                KKJTQ 949
                67776 914
                J4323 177
                22229 704
                96999 178
                8AJKA 70
                69T69 419
                65556 770
                87738 537
                547J4 835
                44766 421
                3355J 684
                2K266 647
                5TTT5 266
                3T3TT 332
                88JJ8 96
                QQ5K3 887
                K7JKK 377
                J8Q2Q 66
                2KKJ6 230
                TTKQT 277
                22Q92 18
                JTTTK 695
                J993Q 846
                88898 728
                88T87 715
                Q74J4 872
                4T999 951
                T997Q 289
                92QA9 726
                AJ394 900
                TT2T2 518
                JJJJJ 565""";
    }

    public enum HandType {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1);

        public final int rank;

        HandType(int rank) {
            this.rank = rank;
        }
    }

    private static final String CARD_RANKING = "AKQJT98765432";
    private static final String CARD_RANKING_WILD_J = "AKQT98765432J";

    private static String sortChars(String hand) {
        return hand.chars()
                .sorted()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.joining());
    }

    private static Map<Character, Integer> repetitionMap(String hand) {
        String sortedHand = sortChars(hand);

        Map<Character, Integer> uniqueOccurrencesMap = new HashMap<>();
        for (Character ch : sortedHand.toCharArray()) {
            if (uniqueOccurrencesMap.containsKey(ch)) {
                continue;
            }

            int frequency = (int) sortedHand.chars().filter(c -> c == ch).count();

            uniqueOccurrencesMap.put(ch, frequency);
        }

        return uniqueOccurrencesMap;
    }

    private static int getHandTypeRank(String hand) {
        Map<Character, Integer> repetitionMap = repetitionMap(hand);
        int uniqueCounts =  repetitionMap(hand).size();

        return switch (uniqueCounts) {
            case 5 -> HIGH_CARD.rank;
            case 4 -> ONE_PAIR.rank;
            // If there are 3 sets of repeating chars, either they are sizes 3, 1, and 1 or 2, 2, and 1
            case 3 -> repetitionMap.containsValue(3) ? THREE_OF_A_KIND.rank : TWO_PAIR.rank;
            // If there are two sets of repeating chars, either they are size 3 and 2 or 4 and 1
            case 2 -> repetitionMap.containsValue(3) ? FULL_HOUSE.rank : FOUR_OF_A_KIND.rank;
            default -> FIVE_OF_A_KIND.rank;
        };
    }

    private static int getHandTypeRank_Joker(String hand) {
        Map<Character, Integer> repetitionMap = repetitionMap(hand);
        int uniqueCounts = repetitionMap.size();
        int jCount = Math.toIntExact(Arrays.stream(hand.split("")).filter(s -> s.equals("J")).count());

        return switch (jCount) {
            case 0 -> getHandTypeRank(hand);
            case 1 -> switch (uniqueCounts) {
                case 2 -> FIVE_OF_A_KIND.rank;
                case 3 -> repetitionMap.containsValue(2) ? FULL_HOUSE.rank : FOUR_OF_A_KIND.rank;
                case 4 -> THREE_OF_A_KIND.rank;
                default -> ONE_PAIR.rank;
            };
            case 2 -> switch (uniqueCounts) {
                case 2 -> FIVE_OF_A_KIND.rank;
                case 3 -> FOUR_OF_A_KIND.rank;
                default -> THREE_OF_A_KIND.rank;
            };
            case 3 -> uniqueCounts == 2 ? FIVE_OF_A_KIND.rank : FOUR_OF_A_KIND.rank;
            default -> FIVE_OF_A_KIND.rank;
        };
    }

    private static List<String> orderHandsOfSameType(List<String> hands, String cardOrder) {
        return hands.stream()
                .sorted(Comparator.comparing((String hand) -> {
                    StringBuilder orderKey = new StringBuilder();
                    for (int i = 0; i < hand.length(); i++) {
                        char c = hand.charAt(i);
                        int index = cardOrder.indexOf(c);
                        orderKey.append(String.format("%02d", index)); // Pad with zeros for correct comparison
                    }
                    return orderKey.toString();
                }).reversed())
                .collect(Collectors.toList());
    }

    private static TreeMap<Integer, String> rankHands(TreeMap<Integer, List<String>> handsGroupByTypeRanking, String cardOrder) {
        TreeMap<Integer, String> handToRank = new TreeMap<>();
        AtomicInteger rank = new AtomicInteger(1);

        handsGroupByTypeRanking.forEach((handTypeRank, hands) ->
                orderHandsOfSameType(hands, cardOrder).forEach(hand -> handToRank.put(rank.getAndIncrement(), hand)));
        return handToRank;
    }

    public static void main(Object... args) {
        int part = Integer.parseInt(String.valueOf(args[0]));
        boolean isDemo = args.length > 1 && args[1].equals(true);
        String dataStr = isDemo ? HAND_TYPE_DATA_DEMO : HAND_TYPE_DATA;
        int expectedResult;
        long startTime = System.nanoTime();

        String[] dataRows = dataStr.split("\n");
        Map<String, Integer> handToBid = new HashMap<>();
        for (String rowStr : dataRows) {
            String[] row = rowStr.split((" "));
            handToBid.put(row[0], Integer.parseInt(row[1]));
        }

        TreeMap<Integer, String> rankToHand;

        if (part == 1) {
            expectedResult = isDemo ? EXPECTED_WINNINGS_DEMO_PART_ONE : EXPECTED_WINNINGS_PART_ONE;

            TreeMap<Integer, List<String>> handsGroupByTypeRanking = new TreeMap<>();
            handToBid.forEach((hand, bid) -> {
                int rank = getHandTypeRank(hand);
                if (!handsGroupByTypeRanking.containsKey(rank)) {
                    handsGroupByTypeRanking.put(rank, new ArrayList<>(List.of(hand)));
                } else {
                    handsGroupByTypeRanking.get(rank).add(hand);
                }
            });

            rankToHand = rankHands(handsGroupByTypeRanking, CARD_RANKING);
        }
        else {
            expectedResult = isDemo ? EXPECTED_WINNINGS_DEMO_PART_TWO : EXPECTED_WINNINGS_PART_TWO;

            TreeMap<Integer, List<String>> handsGroupByTypeRanking = new TreeMap<>();
            handToBid.forEach((hand, bid) -> {
                int rank = getHandTypeRank_Joker(hand);
                if (!handsGroupByTypeRanking.containsKey(rank)) {
                    handsGroupByTypeRanking.put(rank, new ArrayList<>(List.of(hand)));
                } else {
                    handsGroupByTypeRanking.get(rank).add(hand);
                }
            });

            rankToHand = rankHands(handsGroupByTypeRanking, CARD_RANKING_WILD_J);
        }

        int actualResult = rankToHand.entrySet()
                .stream()
                .map(entry -> entry.getKey() * handToBid.get(entry.getValue()))
                .reduce(0, Integer::sum);

        Utils.publishResult(startTime, expectedResult, actualResult);
    }

}
