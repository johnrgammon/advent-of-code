package ca.gammon.advent2023.days;

import ca.gammon.advent2023.days.pojos.Coordinate;
import ca.gammon.advent2023.days.pojos.Direction;
import ca.gammon.advent2023.days.pojos.Grid;
import ca.gammon.advent2023.days.utils.Utils;

import java.util.*;

import static ca.gammon.advent2023.days.Day10.ConnectorType.CONNECTOR_TERMINUS;
import static ca.gammon.advent2023.days.Day10.ConnectorType.fromString;
import static ca.gammon.advent2023.days.pojos.Direction.*;


public class Day10 {

    private static final int PART_ONE_RESULT_DEMO_SIMPLE_LOOP = 4;
    private static final int PART_ONE_RESULT_DEMO_SIMPLE_LOOP_PLUS = 4;
    private static final int PART_ONE_RESULT_DEMO_IN_BETWEEN_LOOP = 8;
    private static final int PART_ONE_RESULT_DEMO_COMPLEX_LOOP = 8;
    private static final int PART_ONE_RESULT = 6942;
    private static final int PART_TWO_RESULT_DEMO_SIMPLE = 4;
    private static final int PART_TWO_RESULT_DEMO_LESS_SIMPLE = 8;
    private static final int PART_TWO_RESULT_DEMO_COMPLEX = 10;
    private static final int PART_TWO_RESULT = 297;

    private static final String PART_ONE_DEMO_DATA_SIMPLE_LOOP;
    private static final String PART_ONE_DEMO_DATA_SIMPLE_LOOP_PLUS;
    private static final String PART_ONE_DEMO_DATA_IN_BETWEEN_LOOP;
    private static final String PART_ONE_DEMO_DATA_COMPLEX_LOOP;
    private static final String PART_TW0_DEMO_DATA_SIMPLE;
    private static final String PART_TWO_DEMO_LESS_SIMPLE;
    private static final String PART_TWO_DEMO_DATA_COMPLEX;
    private static final String DATA;
    static {
        //region DEMO
        PART_ONE_DEMO_DATA_SIMPLE_LOOP = """
                .....
                .S-7.
                .|.|.
                .L-J.
                .....""";
        PART_ONE_DEMO_DATA_SIMPLE_LOOP_PLUS = """
                -L|F7
                7S-7|
                L|7||
                -L-J|
                L|-JF""";
        PART_ONE_DEMO_DATA_IN_BETWEEN_LOOP = """
                ..F7.
                .FJ|.
                SJ.L7
                |F--J
                LJ...""";
        PART_ONE_DEMO_DATA_COMPLEX_LOOP = """
                7-F7-
                .FJ|7
                SJLL7
                |F--J
                LJ.LJ""";
        PART_TW0_DEMO_DATA_SIMPLE = """
                ...........
                .S-------7.
                .|F-----7|.
                .||.....||.
                .||.....||.
                .|L-7.F-J|.
                .|..|.|..|.
                .L--J.L--J.
                ...........""";
        PART_TWO_DEMO_LESS_SIMPLE = """
                .F----7F7F7F7F-7....
                .|F--7||||||||FJ....
                .||.FJ||||||||L7....
                FJL7L7LJLJ||LJ.L-7..
                L--J.L7...LJS7F-7L7.
                ....F-J..F7FJ|L7L7L7
                ....L7.F7||L7|.L7L7|
                .....|FJLJ|FJ|F7|.LJ
                ....FJL-7.||.||||...
                ....L---J.LJ.LJLJ...""";
        PART_TWO_DEMO_DATA_COMPLEX = """
                FF7FSF7F7F7F7F7F---7
                L|LJ||||||||||||F--J
                FL-7LJLJ||||||LJL-77
                F--JF--7||LJLJ7F7FJ-
                L---JF-JLJ.||-FJLJJ7
                |F|F-JF---7F7-L7L|7|
                |FFJF7L7F-JF7|JL---7
                7-L-JL7||F7|L7F-7F7|
                L.L7LFJ|||||FJL7||LJ
                L7JLJL-JLJLJL--JLJ.L""";
        //endregion DEMO
        //region DATA
        DATA = """
                F.F77.F7F7FFJJ..77FF.--L777.-7777--F7F7FJFFF7FF|.F77FF|-7-|-F7-7-FF|FJ-FFJ7-F--F|-L--7L|-7FFFF---7-FJ.7J.F-7J.F|7-LF7L7|.F-L|7...LF--LL7F-77
                |77FL-F7F-FJJF7FJ-FJF.L||FLJF7JFJ7FJ77LJ-7-||L77-F-7||L7|LL.JJ.L-FFJ-|.FJJ|F|FJJ|..|-7.77LLJF7JJ-|7L77J|.|L|-L7|L-|LL-L7-|7.L7F7-FLJ|FFLJ-LJ
                JFFJJFF-|J.|L7.7FL|7|7.LL-J.-JF||J|-L|7|FL7L7FJ|.--7|F---.L-J.|-7JL-F7-7--|7L7.7.F.-JL7L|J.F-JJJ7LFJJLL7F|-7.LF-7.F7LF|L-LFLJL-7FJL|-|7|7.FJ
                LLJ7LJL7||FF-|-J7J||--7J7.FJL7-.F-J.|..F7L|FF|J77LFFLFJL|F7|FLJLFFJ-J7.LJ|L|FJ-F7J.LF|--JF--7|L||7.|JJ|LFJF7J-7.|.|J-|-JFFJ|J7|L|--F.--F77.7
                LLLJL|-7-7-|-F7-77.JJ.|-F-7J||L-F7.|.7-L|7JFLJLJ|.7--|7JL.FJJJ-F.L.7F-7JF7J|JL|LJ-..F7|7.F-LL-FF-|7J|FLJ|.J.FFF7F7JJLF.|F7LL.F|7L7-|7JJJLF.L
                F|F|F||L.|L7|||FJ|7L--JFL7L-|-7.7--JJ|LLJ7LFJ.LL|.LJFLL7.F.|7F-LJJ-JLLJ.|7J|LLF7J.|7|L-77|-JLF7J7LJF-77F|.||7.F7||.|.L-|-|.FFFJJ7JFLF7-F|J.|
                -77.|J7|-7.L-J7L-F77L|J77LFLL||F|7FF7|..|J.L-L-.L7L|L7FL77.-7L7J||.F-LJ-JJ.|7||L-.FFL-7L7J.JJLL7F77F-L77|--L-F|LJ|-77|7|J|-7-LL.|LL7||F-JFL-
                L7--|F-F7..FLL|7FJJL-7-F7L--L|F|LF|.|F-F7-7J.|F|.|-7F7-||J|7||L7L|F-7.LJ|.F77|--L7F7F7|FJ.FL7.||..FJ-7--F--|.LL7FJ.L|-JJ|.FL7J.LLF-|--L-7--L
                7.|L-7.|7|777..|FJF7-|LLF7|.|LF7F7L7.L-LJFJLF|-F-7L|J...L-L|J-J-|||L7J|FL---J|FFLF|||||L-77-L|-JFF|7FLJFL7FF7-FJ|FL-J.|77|-|--JJ-|7.7-L-|FLL
                |F|7-|.L||L-J-J7L7L7F|7-7-FF7.||-F7|F7F|F7J7--7L7|-L.7FF.|L|7FJ-77.L|.7-7|FJJ--7|F|||||F-JL7L|LF7FL-7JF7LF-JL7|FJ7.77-J7F77LF|.|.|JJ|7|--7FL
                7-J7-J--J|-|||.FF7F-7F7F77||L-JL--7-JF7FJ|J|FJ7.|L7F--7F-|-77|7..|7F77|LL||||-|.FFJLJLJ|J|L|.FFJ|-J|77LJ-L--7LJL-7.7J7||-7JF-L7--JLLLJJ.||F7
                -7JF7|.J7|F7F7.F|||FJ|||L7-L7F--7FJ.FJ|L7|F7J-FF|FJ-|-L|F--F7|FLJ-7LJ-|-J|FF|FLF-JF---7|F7F7-FJFJJ.L-|||J.F-JF7F-J7LL-JJ.|FL7J.7-77FFJ...L|J
                F7.LJ|FLLFFJFL|-|||L-JLJFJF7||-FJL-7L7|FJ||||.LFJL-7.|J|-L7||77J.L|JFF7J---|L7.L--J.F-JLJLJL-JFJJLF7FFF7.FL-7|||LFFF7J7.F|J-J.LF-77JLJF77L-J
                FJ|J|-7|L-JFFF--J||F7F--J7||LJ-L-7FJ-||L7||L-7.L7F-J-F-7JLFJ|JL-.LLF7|L7-LFJ.|FF7|F7L--7F-----JF7L|-F7|L7F7|||LJ-F7F7JF-FL--F-|LFL|FLF-F7-|7
                |F|.J-J77JF|.L--7|LJ|L-7F7||F77LFJ||FJL7|||F-J7FJ|F7FJFJF7|FJF7LFF7||L7|F-7L-F-JL-JL7F7|L7F7F77||F7.|||FJ||FJL-7-|LJ|FJJ.|LL|7|FJ|JF7|FL||.F
                F--7J|LL77|.FF--JL-7|F-J|LJ|||F7L7|FJF-J|LJL7F7L7LJLJFJFJLJL-JL--J||L-JLJFJ-|L-----7|||L7||LJL7|||L7||||FJ|L--7L-JF-JJL-F77.|LJJ-J.J7|-JLFF7
                JJJ|F7FFJ-L7FL7F--7LJ|F7L7FJ||||FJ||FJF-JF--J||FJF---J|L---7F-----JL-7F--JLFF7-LF--JLJL7|||F--J|||FJ|||||FJF77|F7FJF7LF||F777.|.F|F|||J7-FJJ
                L77.JJ--J|.FJ.LJF7L-7LJL-J|FJ|||L7|||LL-7|F7L||L7|F-7-F-7F7|L-7F7F---JL--7-FJL7-L---7F7LJ|||FF-JLJL7|LJ||L-JL-J|LJ7|L7F7FJL77-7-77-F-J-F---J
                .L|7--7|-FFF--7FJL-7L--7F-J|FJ||FJLJ|F-7|LJL7|L-J|L7L7L7||||F-J||L7F7F---JJ|F-J-F7F7||L-7LJL-JF7F--JL-7|L--7F-7|F7F|FJ|LJF-J--F7LJJ.-J.J.FL.
                7J||F7L---JL-7|L7F7L7F-JL7FJL7||L7F-J|FJ|F--JL7F7|7|FJFJLJLJL7FJL7LJ||F--7-||F7L|LJ|LJF-JF----JLJF7F7FJL7F7LJFJLJL-J|FL-7L7..FJF77.|.F|JF|..
                -77F-FJ-JJ7FL||-LJL7|L-7FJL-7|||-||F7|L7|L-7F7||LJFJL7L---7F-JL-7L7FJLJF-JFJ||L7L-7L77L-7L-7F7F7FJLJ|L-7LJ|F7|F-----JF7FJFJF7JFF-77L--.|-F|J
                |LL77||L||L-7|L----JL--JL---JLJL7|||LJFJ|F-J|LJL-7L-7L--7L|L-7F7|FJL7F7L7-L7LJFJ-FJFJF7FJF-J||||L--7|F7L7FJ||||F7F---J|L7L7||F7|FJ--7-F7JJLL
                |7-L7|J.L|.LFL-7F-7F7F----7F--7FJ|||F7L-J|F7L---7L7FJF--JFJF-J|LJ|F7LJ|FJF7L-7|F7L7L7||L7|F7|LJ|F7FJ||L-J|-|LJLJLJF7F-JFJFJ|||||||.F7.-J.-J|
                LJJL-7-|J||L7|JLJ|LJLJF7F7LJF-JL7|||||F--J|L7.F7|FJL7L-7JL7L-7L-7||L7L|L7|L7FJ||L7|FJ|L-JLJLJF-J|||FJL--7L7L-7F---J|L7-L7L-J||LJL-7-|7|.|JL|
                |JL77L-J--JJ|F7F-7F---JLJ|F-JF-7|||||LJF7.|FJFJLJ||FJF-JF7L7FJF7|||FJFJFJ|FJL7|L7LJ|-L--7F7F-JF-J||L---7L7|F7||F7F7L-JF7|F--J|F---J|L7JF|-7|
                |.-J7|7|FLJF7|||FJ|F--7F-JL--JJ|||LJL7FJL-JL7L--7L7L7L7FJL-J|FJLJ|||FL7|7|||J||.L-7L7F7FJ|LJF7L-7||F---JFJLJLJLJ||L7F7||||F7FJL7J7LF-|.L||FF
                LJJ.FF7-7JLF7|LJL7||F-JL7F-----JLJF--J|F---7|F-7L7|FJFJ|F--7||F--J|L7FJL-JL7FJ|F-7|FJ||L7L7.||JFJ||L7LF7L---7F--JL7LJ||LJLJLJF7L-77L-J-.-|FJ
                JJ|.77JL7--||L-7FJLJL--7|L------7FJ-F7|L7F7LJL7|FJ||FJLLJF7|LJL--7|FJL---7FJL7|L7|||FJ|JL7L-JL7L7||FJFJ|F-7L||F7F7|F-J|F7F-7FJL-7L7JL|FF||F7
                LFF7LFJJ..||L--JL--7-F7|L7F----7|L7FJLJFJ|L7F-J||FJ|L77F-JLJF----J|L--7|FJL-7|L7||||L7|F7|F---JFJ||L-JFJL7|FJLJLJLJL--J|LJ.|L--7L-JJJ.F7LF.F
                LL-7FL-7F--|F7F-7F7L7|||FJL---7||FJL-7FJFJFJL-7||L7L7L7L---7L7F7F7L--7|FJF--J|FJ|||||||||||7F77L7LJF--JF7||L-7F7F------JJF7L--7L-77J.F-JF..|
                JJ|7.|-F|JJLJLJFLJL7LJLJL-7-F7||||F7FJL7|FJF7.||L7L7L7L7LF7L7||LJ|F7FJ||FJ7F-JL7||||FJLJLJ|FJL7-L-7L7F7|LJ|F7||LJF-7F7F7FJL--7|F-J--7|.77-FJ
                F7L---7L-JF|F--7-F7L---7F-JFJLJLJLJ||F7LJL7||FJ|-L7|FJFJFJ|FJ||F-J||L7|||F7L--7LJ|||L-7F--JL7FJF7FJFJ||L-7||||L--JFJ|||||F---J|L-7.LL-7||LJ.
                F77JF-J|JFLFJF7L7||F7F7||7FJF-7F7F7LJ|L---J||L7|F7|||FJ7L7|L7|||LFJL-JLJ|||-F7L7FJ||F7|L7F7FJL7||L7L7||-FJ||||F7F-JFJLJ||L---7L-7|FLL7.|-7F-
                LJJJ|FL7F|.L7|L7LJLJLJLJL-JFJFJ||||F7|F7F7FJL7|LJ||LJL7F7||FJ|||FJF-----J||FJ|FJ|FJ||||FJ|LJF7|||FJFJ||FJFJ||||LJF7|F--J|F---JF7LJJ.J||LL--L
                |..|.-J||LJ.LJJL-7F7F-7F-7FJFJFJ||LJ||||||L7FJL-7||F--J||||L7||||FJ7F7F7F|||FJL7|L7|||||.L7FJLJ||L7|J||L7L7|||L7FJLJ|F7FJL---7||J|.|FF|7|LF|
                |.FFJJ77JFLF-77F7LJLJ|||FJL7L-J-LJ-FJ||||L7|L7F7|||L7F7|LJ|FJ||LJL7FJ||L7|||L7FJL7|||||L7FJL-7FJL7||FJL7|FJ||L7||F--J|LJF----J||7JJ.F|.LJ.J|
                7-F|7F.L7L.L7L-JL-----J|L--J7F-----JFJ|||J||FJ|||||FJ||L7FJL7|L7F-J|J|L7LJ||L|L7FJ|||||FJL-7FJ|F-J|||F-J||F||7|LJL7F7L7FJF-7F7|L7JJ.||FJ.-JF
                |FJ|F--7LJ7.L------7F-7|F7F--JF7F7F7L7|||FJ|L7|LJ|||FJ|FJL7FJ|FJL-7L7|FJF-JL7|FJL7|||||L7F-J|FJL-7||||F-JL-JL7|F--J||FJL7|FJ|||FJLLFJF|...77
                L7-FLJFJLF-F------7LJFJ|||L---JLJLJL7LJ|||FJFJL-7|||L7||F-J|FJL7F-JFJ|L7L7F7|LJF7||||||FJ|LFJL--7||LJLJF7F7F7LJL7F7|LJF7LJ|FJLJL-7FF|FLFFL.|
                ||7.L-J|F-7L--7F-7L7LL-J||F--7F7F7F-JF-J|LJFL7F-J||L7||||F7|L7FJ|F7L7|FJ||||L--J|||||||L7L7L-7F-J||F-7FJLJLJL7F7LJLJF7|L-7LJF----J777||JL|7F
                |-|77.|L7LF---J|FL7|F7F7||L-7LJLJ|L-7L-7L7F--JL7FJ|FJ|LJ||||FJL7LJL7|||F-J||F--7||||||L7L7|F-JL-7||L7|L-----7LJ|F7F7||L-7|F7L----7F7||7-F|L7
                LF|-FF7L7JL----JF-JLJLJLJ|F-JF--7|JFJF7L-JL-7F7|L-JL7|F-J|||L-7L-7J||LJ|F7||L-7|||||||FJFJ|L-7F7|LJFJL-7F--7L-7||LJ|||F-JLJ|F-7F7|||-|L-LJ--
                |FJF-J|L|-|-F-7FL-------7|L--JJFJ|FJFJL---77LJ|L--7FJ|L-7|||F-JF7|FJL7FJ|||L7FJLJ||||LJFL7L7|LJ||F-JF7FJL-7|F7||L-7|||L---7LJJLJ|LJ|.L|J.|.|
                -L7LLF7.77.FL7|F7F-7F7-FJL-7-F7L7|L-JF7F-7|F--JF7FJL7L7FJ|||L7FJ|||F7|L7|||FJL7F7LJLJF7F-JFJF7-LJL-7||L---JLJLJL--J|||F7F7L----7|F7|7F7-|7-7
                |FL7L-|-J|.LFJLJ|L7LJL7L7F7L7||FJL---J|L7|||F7FJ||F7L7|L-J||FJL7||LJ||FJ|LJ|F7LJL7F--JLJF7L-JL7F--7LJL-7F7F7F--7F-7LJ||LJL--7F7|LJ|L7JL7.|||
                L7.77F|-||7-L--7L7L--7||LJL7LJLJF----7|FJLJ||||FJLJ|FJL7F-J|L7FJLJ|FJ|L7L7FJ||F-7|L7F7F7||F7F7||F7L----J|LJLJF7LJFJF7|L---7FJ||L7FJFJ-7J|FLJ
                |FJJ.||||JF-7F7|FJF--JL----JF7F7|F---JLJFF7||LJL-7FJL-7|L-7|FJL---7|FJL|FJ|FJ|L7||FJ|LJ||||||LJLJL-7F---JF-7FJ|F7L-J||F7F-JL-J|FJ|FJ-FLFJ7|7
                -JJFF7-F7FL7||LJL-JF7F7F--7FJ||LJ|F-7F7F7|||L---7|L-7FLJF-J||F7F7FJ||F-J|FJ|FJFJLJL7|F-J|||||F----7LJF--7L7|L7LJL7F7|LJ|L----7LJ|LJJ-FLJJF.|
                |-J7|.L|L7FJ|L-7F-7|LJLJF7LJ.LJ7FJ|FJ|LJLJ||F7F-J|F7L7F-JF7||||||L7LJL-7|L-JL7L--7-LJ|F7|LJLJL--7FJF7L7FJFJL-JLF7LJ||F7|F----JF7F7|7F7J7LJ-J
                |7F|LF-JFJL7|F7LJ|LJFF--JL7F7LF7L-JL-JF---J|||L-7||L-JL-7|||||LJ|FJF---JL-7F7|F7FJF--J||L---7F--JL-JL-JL-JF7F--JL-7|LJ||L7F7F7|LJL-777F77-|7
                L-FL-L-7|F-JLJ|F7F--7L-7F7LJL7||F-7F--JF-7|LJL--J|L--7F-J|||||F-JL7L---7F7LJ|LJLJ-L--7||F7F-JL-7F7F7F-----JLJF7F7FJ|F-JL7LJLJLJF--7|JJL7LF-L
                |L7|LF7||L7F-7LJ|L7FJF7LJL--7|||L7|L---JFJF-77F7|L7F7||F7||||||F7FJF-7FJ||F7L--7FFF--J|LJ||F7F7LJLJLJF-------JLJLJ-LJF7LL----7FJ|-||J7L|JL..
                |--J.|LJL7LJFJF7L7|L-J|LF7F-JLJL-JL-7F7FJ||FJFJL-7||LJ||LJ||||LJ||JL7LJFJ|||F-7L7FJF-7L7FJ||||||F--7FL7F--------77F7FJL-7F--7LJ-|-LJFJ-L|..-
                L7JLLL-7FJF7L-JL7|L7F7L7|||F----7F-7LJLJF7||FJF7FJLJF7LJF-J|LJF-JL-7L-7L7||||JL-JL7L7L-JL-J|LJL7|F7L--J|F--7F7F7L-JLJF--J|F-JF--77FF-JF--7F7
                7J7.||FJL-JL-7.FJL-J|L7LJ||L7|F-J|7L--7FJLJLJFJ||F-7||F7L-7L-7|F7F7|F-JFJ|||L---7LL-JF-----JF-7LJ|L---7|L-7|||||F-7F7L---JL--JF-JJ-7J-L|-F-J
                LF|-LLL-7F7F7|FJF7F7||L-7|L-JFJF7|F---J|F-7F-J.LJ|FJ||||F7|F-JLJ||||L7FJ-||L7F--JF---JF--7F7|FJF-J|F7JLJLFJLJLJLJFJ|L--------7L-7J|JLF7|7.FJ
                |FJL|JLLLJLJ|LJFJ||||F--JL---JFJLJL----JL7|L--7.FJL-JLJLJ|LJF7FFJ|LJFJL-7||FJL-7FJF7F7L77LJLJL-JFF-JL----J|F-7F77L7L----7F--7|F-JLJ7FFJ|LFJ|
                F|7|F7F|LF7LL--J7LJLJL--7F7F7FJF----7F7F7|L7F-JFJF7F7F---JF7|L7L7|F7L-7FJLJ|F7FJL7||||FJF-------7|F7F-----7|FJ|L77L-7F--J|F-J||7LFFF7|F-LL-|
                FL--J--F-J|F-------7F---J||||L7L-7F7LJ||LJ-LJF7|FJLS||F7F7|LJFJL|LJ|F7|L--7||||F-J||||L7L----7F7|||LJF----J|L7|FJF7||L---JL-7|L77-JF|7|LL|7|
                |7L|.|LL-7LJF-7F--7LJF7F7|LJL-JF7LJL-7|L-7F7FJLJL-7FJ||||LJF7|F7L--J|LJF7FJLJ|||F7|LJ|FJF--7|||||LJF-JF----JFJ|L-JL7L---7F7FJL7L-7FFLJ.F.|-J
                LL--77LLFJF7|-LJF-JF-JLJLJ-F7FFJL-7F-J|F-J|||F----JL-J|LJF-JLJ||.F7|L7FJLJLLFJ|LJLJF7||FJF7L-J||L7FL--JF---7L-JF7F-JF7F7LJLJF7L--JJ||-|-7J7|
                F|LLJJF.L7|||F-7L--JF7F7F7FJL7L--7|L-7|L-7|||L-7F7|F7FJF7L7F7FJ|FJL-7||-L7..|FJ|F7-|LJ||FJ|F--J|FJF7|F7L--7L-7FJ|L--JLJL----JL---7.||LF-L7J7
                -|F|7-7FL||||L7L----JLJ|||L-7|FF7|L--J|F-J|||F-J|L7|||FJL-J|||FJL7F-JLJ|JF--LJF-J|.L7FJLJFJ|F7FJL-JL-JL---JF7LJFJF-7F---7F----7F-JF7-7J7||.J
                F77.JJLJLLJLJ-L-------7||L--JL-JLJF--7LJF7||||F7|FJ|||L-7F7||||F7|L-77FF-7.FLLL-7|F7LJF77L-J||L-7F7F7F----7|L7LL7|FJL--7|L---7|L7.FJF7||-|7J
                FLJ7LF-||.F7LF--------JLJF-7F---7FJF-JF-JLJLJLJLJL-J||F-J|||LJLJLJF7L--JFJ7F|.F-JLJ|7FJL7F7FJL-7||LJ||F---J|FJF7LJL-7F-J|F7F-J|FJ.|L7-J||LJ.
                LJFJ|.FF-FJL-JF-7F7F7F-7FJFJ|F--J|LL7FJF--7F--7F---7LJL--JLJF7F7F-JL7F--JF77.FL-7F7L7L7FJ||L-7FJLJF7LJL--7FJL-JL---7|L-7LJ|L-7||F--FJ|FF7-|7
                JFF.L-7J7L----JFJ|||LJFJL7L-JL7F7|F-J|FJF7|L-7|L--7L7F---7F7|||LJ||FJL---JL77F7-LJL7L-J|FJL--J|F7FJ|F----JL7F------J|F7L-7L--JLJ-7.J---.-7L7
                J7J7.|J7FF-----JFJLJF7L7FJ.F7|LJLJL--JL7||L--JL---JL|L--7|||||L-7F7|F-7F---J-JF.JLLL--7||F---7LJ|L7|L--7F--JL-----7LLJL--JF--7LL7F--7-|7|||L
                ||L7-LJ|7L------JF--JL7LJF-J|F--------7LJL7|F7F--7F7L---JLJ|||F7LJ|LJJLJ.JLL|-JJ7JF7F7||LJLF-JF7L-JL7F7LJF---7F---JF---7F-JF7||||JLFFJ|LFF-J
                FFLJF|FF--------7L---7|F7L-7|L-----7F7L7F-JFJLJF-J||LF---7.LJLJL-7|LL7|.7J|FF-F|7.|||||||F7L-7|L7F-7LJ|F7L--7LJF---JF-7LJF7|LJ7F|-FJJFL7LL.L
                |J|FL7FL-----7F7L7F--JLJL--JL------J|L7|L--JF-7L--JL-JF-7L------7LJ|7F-7L.LFJF----JLJLJL7||F7LJJLJJL-7LJL---JF7|F---J|L7FJLJJLJF|-|L||7J.|FL
                ||F|JL7J|F-7FLJL7|L-7F--------------JFJ|F-7FJLL7F-7F--JLL-----7FJJFL-FJ77F-L-|F--------7||||L7F7F----JF------JLJL--7F-7|L---7|||L7|--|--7-L.
                F-J|7JJ.FL7L7F--J|F7||F--------------J|LJJ|L--7LJ-|L---7F----7||7F|FL|J-L77|FLJF-------JLJ||FJ||L---7FJF-----------J|FJL---7L--7L-J7FJ|J|..J
                |.|-J7...LL7LJF-7LJ|LJL---7F----------7F-7|F-7L--7L7F-7LJF7F-J|L7F-7F|-7-7LL-F-JF7F7F-7F-7LJL-JL--7LLJFJF7F7F----7FFJL----7L---J|..77--777-L
                FF-.|7FJ7JLL7FJ-|F7L7FF---J|F7F7F-7F--J|FJLJ-L-7FJ|||FJF7||L-7L7LJFJ-F7L7|-.|L-7|LJLJJLJ.L-7F-7F-7|F7-L-JLJLJF7F7L-JF-----JF7F7F|F--J-JL-F7.
                F7.F7FJ|7.FF||F-J|L7L-JF--7||LJ|L7|L---J|F7F7F7LJF7LJL7|||L--J.|F-J7|J|FLJJ7F--J|J|FF------J|FJ|FJLJ|F7LF-7F7|LJL---JF7F7F7|||L-77.|-|L7.|L7
                FF7.L|.LLF--J|L7FJLL-7FJF-J||F7L-JL----7LJLJLJL-7|L--7|||L---7-||J-LJJF|JJLFL---J.F7L7F--7F7|L-JL--7LJL7L7LJ||F7F7F7FJLJLJLJLJF7L77JF|||.|FJ
                F..F-|-|LL-7FJLLJFF--JL7|F7|LJL7F7F---7|F----7F-J|F-7||||F---JLLJ||.FFF7F7.F-7LF7FJL-J|F7LJLJF7LF--JF-7L-JF7|LJLJLJLJF--------JL-JL|-J-F-F||
                |-F7F|-JFF7LJF7F-7L----JLJLJLF7LJLJF--J|L---7|L--J|-LJLJ||F-7J.|.FJFF7|LJL7L7|.|||F-7FJ|L--7FJ|FJF-7|FJF--J||F------7L--7F7LF7JL|-F7-|-7.LJJ
                |..|7|7F7|L7|||L7|F-------7F-JL----JF7FL----J|F7F7L7F7F7LJ|FJ|.L7|LFJ|L--7|FJL7||||FJL7L--7|L7|L-JFLJL7|F--JLJF7F--7L--7LJL-JL7FF77|.L.L.|..
                J7----F-7L7|FJL-JLJF-----7LJF7F-----JL-7F-7F7LJLJL-J|LJL--JL--7.LFFL7L7F7|LJF7|||LJL-7|F--JL7||F7|F7F7LJL7F77FJ||F-JF-7L7F7F--JFJL-7-|7L----
                .F-|7JL7L-JLJF7F7F-JF----JF7||L7F------JL7LJL-7F7.F-JF--------JFF.LLL7LJ||F-JLJ|L7LF7LJ|F7F7LJLJL7|LJ|F-7||L-JFJ|L-7L7L7LJ|L-7LL7F-JF-7FLJ-L
                JLFJ||.|F7F7FJLJ|L-7L7F7F7|||L-JL7F7F----JF---J|L7|F-JF------7F7F77-FJF7LJ|F7F7|FJL|L-7LJLJ|F7F-7|L7FJL7|LJF-7L-JF-JFJFJF7L--JF-JL--JFJ-.77.
                FJJFF7FLJLJLJ.F7L--J7LJLJ||LJF7F7LJLJF----JF7F7L7LJL--JF--7F7||LJL7FJFJ|F-J|||||L-7L7FJF-7FJ|||FJL-JL--JL--JLL7F7|F-JFJFJL---7L7F----J.|.L|7
                F|F|L|7J|-LF--J|LF7F7F-7FLJF7|LJL----JF---7|LJ|FJF-7F7FJF7LJ|||F--JL7|FJ|F7|LJLJF-JFJL7L7|L-JLJL------7F----7FJ||LJF-JFJF----JFJL----7F--FF-
                .77L-F--J7LL--7L-JLJ||FJF77||L------7FJF7FJ|F-J|FJ.|||L7|L7|LJ||F7||LJL7LJ||F--7L-7L-7L-J|F------7F7F7LJF---JL7||F7L-7L7L-----JF-----JJJ-L.|
                .L7JJ|.FJF|F7FJF---7LJL-JL-JL-------J|FJ|L-JL7J||F7LJL7|L7L7LFJLJ|F7.F7|F-J||F7L--JF7L--7||F----7||LJL77L----7|||||F7|FJF-----7L-----7J|7.F|
                FL-7-7-J7F7|||FJF7FJF----7F--7F------J|LL7F-7L-JLJL--7||LL7L7L7F-J||FJLJL7||LJL-7F7||F--J|LJF---JLJF-7L------J||LJLJ|LJFJF7F7.L7F-7F7|.L---J
                L7LJ.|.|F|LJLJ|FJLJFJF---J|F-J|F7F7F-7L-7LJFJF7F7F---JLJF7L7L-JL-7||L---7L7L7F--J||||L7F7L--JF----7L7L7F-----7|L--7FJF7L-JLJL7|LJFLJ|L7JFLF|
                |JF-FFL|7L----JL-7FJFJF7F-JL--J|||LJ|L--JF7L-JLJ||F---7FJ|FJF----J||F--7|FJL|L-7FJ|||JLJL---7L7F--JFJFJ|F----JL---JL-JL7F7F-7L-7F7F7L-J77F||
                F7L-J|.FF7F7LF---J||L-J||F-----JLJF--7F--JL----7|||F--JL7|L7L---7F||L-7LJL7FJF-JL7LJ|F7F----JFJL7F-JLL-JL--7F7F--7F---7||LJ7|F7LJLJL-7-7||-|
                FL--L|-||LJL-JF---JF7F-J|L7F------JF7LJF7F7F---JLJ||F-7FJ|FJF--7|FJL7FJF-7||FJ-F7L-7LJ|L7F-7FJF7||F-7|F----J||L7FJ|F--J|L7F7LJ|F-7F7FJJJ7|F7
                |7J|L7.FL-----J.F7FJLJF7L7LJF------JL--JLJLJF----7|LJFJL7|L7|F7LJL7FJL-JFJLJ|F7||F7|F7|FJ|FJ|FJLJ|L7L-JF-7F7||FJL-J|F-7L7LJL-7|L7LJ||JJFFF7J
                -77LF---.LF7F---JLJF7FJL-JF7L---------------JF7F-J|F-JF7||L||||F7FJL7F-7L--7||||LJLJ|LJL-JL-JL-7FJ|L7F7|FJ|||LJF--7LJFJL|F7F-JL7L7FJ|J-LJL||
                .LJ7|.LLJJ|LJF---7FJLJF---JL--------7F7F7F7F-JLJF-J|F7|||L-JLJ||||F-JL7|.F7|LJLJF7F-JF-7F-7F---J|F-7LJLJL-JLJF7L-7L7FJF7LJLJF-7L-JL-J.L|--J7
                .|-F-7F-J-L-7|F--J|F7.L--7F----7F7F7LJLJ|||L---7L-7|||||L--7F7LJLJL7F7||FJLJF---JLJF7|FJL7|L---7|L7L-7-F7F---JL-7L7|L-JL---7L7L-777-J--|J.F|
                FLF||JLJJFFFJ|L---J||F7F7LJF--7LJLJL---7LJL7F7FJF7|||LJ|F-7LJ|F----J|||||F-7|7F7F7L||||F7||F-7FLJFJF-JFJLJF7F7F7L-JL---7F--JJ|F-JF7FJ7||7FF|
                J|LLJ.7JF7FL-JFF--7||||||F7L-7|F7F7F--7L--7LJLJ|||||L-7||FJF-JL---7J||||LJFJL7|LJ|FJ||LJ|||L7|F7FJFJF7L---JLJLJL----7F7|L--7FJL7FJL7.|7LJ--J
                L7-LJ.J.-|-LF-7L-7LJLJLJLJL--JLJLJ|L-7|F--JF-7-FJ||L7||LJL7L-7F---JFJLJ|LFJF7LJF-J|FJ|F-J||FJ||LJFJ-||.F7F7F7F7F----J||L---JL-7LJF7|-JJ.LJJ.
                FJL|-|.FL|7LL7|F7L--------7F7F7F-7L--JLJ|F7L7|FJFJ|FJFJF--JF7|L---7L--7L7L-J|F-JF7|L-JL-7||L7|L-7|F7||FJLJLJ||LJF7F--JL---7F7-|F-JLJ7F.F7|LF
                JJ-7-JF-7L77FJLJ|F-------7LJLJLJ7L-7F7LF7|L7|||FJFJL7L7L7F-J||F---JF7LL7L7LFJL--J|L-7F--J||FJL7L||||||L---7FJL--J|L7F-----J||FJL7F7|F77-F-.|
                |F-|.LL7|-J7L--7LJF7F---7L---7F---7LJ|FJLJFJ|LJL7L-7|F|FJ|F-J|L-7F7||F7|FJFJF----JF7|L7FFJ||F-JFJLJ|||F7F-JL7F7F7L-JL-7|F7FJLJF7LJ|FJ|J-7.F7
                F|-|-.F--JLFF--JF7|LJ.F7L---7|L-7FJF-JL-7FJ7L--7|F7||FJL7|L7FJF-J|||||LJL7|FJF7FF7||L7L7L7|||F7L7F-J||||L7F7LJLJL-----JFJLJF7FJ|F7LJFJJ7L--7
                F|7|.-F7L7LLL---JLJJF7||F---JL--JL-JF7F7||F-7F7|LJ||||F-J|FJL7L7FJ|||L--7||L7||FJ|||.L7L7||||||FJ|F7|||L7LJL----7F7F7F-JF--JLJJ|||F7L7L77|.L
                -JL-JL|FF7.-F-------JLJLJF----7F---7|||||||FJ||L-7|||||F7||F7|FJL7|||F7F||L7||||FJ||F7|FJ||||||L7||||||FJF-7F--7LJLJLJF7L---7F7LJLJL-J.F---|
                |7L|.FJJL7FFL----7F-7F-7FJF---J|F--J|LJ||||L7|L7FJ||LJ||||||||L7FJ||||L7|L7|||LJ|FJ||||L7|||||L7|LJ||||L7L7|L-7|F-7F-7||F7F7LJL7F-7JL|.L-JJJ
                F|F-J-|LF7-F-7F--J|FJ|FJ||L---7||F-7L-7LJ|L7||FJ|FJL-7LJ||||||FJL7LJ|||LJFJ|LJF-JL7LJ|L7||||LJFJL-7||||FJFJL--J|L7||FJ|||LJ|F-7LJFJJFL-7.|-F
                |LJJJ|F-JL-JFJL-7FJL7|L7|F7F--J|LJFJF7L7FJFJ||L-JL7F7L7FJ|||||L-7|F7|L7F-JFJF7L7F7L7F|FJ|||L7FJF-7|||||L7L----7L-JLJL7||L-7||FJF-JJFJJL-7|F7
                L7||.-L----7L--7LJF-J|-LJ|LJF-7|F-JFJL7|L7|FJL-7F-J|L7|L7|LJLJF-JLJ|L7||F7L7|L-J|L7L7|L7||L7|L7L7||||||FJF-7F7L--7F7FJ||F-JLJL7|J|FLJ7-LJLLJ
                LJF-7-J.FJ.L--7L-7L-7|F--JF7|FJ|L-7|F7LJFJ|L7F-J|F7L7LJ7LJLF--JF7F7L7||LJL-JL--7|-L7||FJ|L7LJL|FJ||LJLJL7L7|||F7FJ||L-J|L--7FL|||F7JFL7F.7F-
                FLJ-J7JL7LJ.F-JF7|F7LJL---JLJL7|F7|LJL-7L7|J|L7FJ|L7L--7-F-JF7FJLJ|FJLJF7F-----JL-7LJ||J|FJF7FJL7LJF----JFJ||LJ|L-JL--7L7F7L7LLJ-JFF7.FJL|J7
                -7.FJL7.J-JFL--J|LJL--7F--7F7|LJ|||F--7L7|L7L7|L7L7L7F7|FJF7||L7F-JL--7|LJF-7F7F7FJF-J|FJ|FJLJF7L-7L7F--7L7|L7.L----7FJFLJL-JL|JLL7JFL77-LJ|
                F-J|F7L7.J.LL-F7L----7|L7FJ||F--JLJL-7|FJ|FJFJ|FJFJJ||||L-J|||FJL7F7F7||F7|FJ|||||JL-7|L7|L7F7||F7|FJ|F-JFJL-JF7JF-7|L------7.|.-J.FJ|J.F|-J
                |7.LLJ7||7F7-FJL-----JL-JL-JLJF---7F-J|||||FL7|L7L7FJ||L7F-J||L7FJ|||LJLJLJL7|LJ|L--7LJFJL7||||LJ||L7|L-7|F---JL-JFJ|F7F7F--J7FJ--||J|7.F-J7
                L-L..LJ|L7JL-L---7F7F-7F-7F7F7|F--JL-7|L7|L-7|L7|FJL7||FJL-7||FJL-JLJF7JF7F-JL7FJF-7L-7|F7||||L77|||LJF7|LJF-7F7F-J.LJLJ||J..|7J.|F-.77-||-J
                F|L7-LFJ--7FF---7LJ||FLJFJ|||||L-7F7FJ|FJ|F7|L7||L7J||LJF--J|LJF7FF7FJL-JLJF7FJ|FJLL7FJLJ||||L7L7||F--JLJF-J-LJ|L------7|L77-L|.F.JL|||FJ.|7
                77.|F7LL-||LL-7FJF7LJF7FJFJ|||L7JLJ||LLJ7LJ||FJ|L7|FJL7FJF-7L--J|FJLJF7F-7FJ||FJL-7FJL--7||||FJFJLJL7F7F7L---7FJF7F----JL7L-7-J-|.LJLJ7-.F-7
                ||.|J7.FF77|F-JL-JL--J|L7L7|||FJF--JL-----7LJ|FJFJ||F-J|FJ|L-7F-JL7F-J||FJL7|||F7FJL-7F-JLJ||L7|F7F7||||L7F7FJ|FJ|L----7LL--J7JFL-|LJ.LJ7|-|
                L-7L-|.7.L--L-7F7F7F-7L-JFJ|||L7L--7F--7F7|F-J|LL7|LJF-J|F77FJL--7LJ.FJ|L7FJLJLJ|L-7J|L--7FJ|FJLJLJ|||LJ.||LJ-LJJ|F---7|JL|J-7-FJFJ7L|.FL--|
                |L|J|J..7.|7F-J|LJ||7L7F7L7|||FJ|F-JL7FJ|LJ|F7L-7|L-7L-7LJL7L7F-7L7F-JFJFJL7F7F-JF-JFJF7FJL7||F7F7FJ||F--JL7F---7|L--7LJL||.7LF|J|JL.FL7JJF7
                |-|.--.F-J7FJF-JF-J|F-J|L7|||LJF-JF7FJL7L-7LJL7FJ|F-J.FJF-7L7LJ.L7||F7|FJF7||LJF7|F7L-J||F7||LJ||||FJ||F7F7LJF--JL---J|F77J|7LL7.F7|7JL|-JL|
                JFL7.JFJJL-L7L7FJF7|L--JFJ|||F-JF7||L-7|F7L--7|L7LJF--JFJFJFJ-F--J|LJ||L7|LJL-7||LJL--7|LJ|||F-J|||L-JLJLJ|F7L7F7F--7F-J|77.|7.J-|-7F7J.|-J|
                L7LL|L-J-7LFJFJ|FJLJF---JFJ||L-7||||F-J||L7F7|L7L-7L--7L7L7|F7|F7FJF7||F|L7F--J||F7F7FJL7FJLJ|F7|||F-----7||L7LJLJF7LJF-JLLF7L7.||7L7..L||.|
                .|.F7F|LF|FL-J7||77-L7F7FJ7LJLFJ||||L7L||-||LJ|L7FJF7FJFJF|LJ|||||||LJL7|FJ|F7FJ|||||||J|L--7||||LJL----7|||FJF7F-J|F-JJL7FLL.FFFJJLLL7.-7FJ
                FF77LF--F7-L|JF||F7F|LJ||FF7F-JFJ||L7L7||FJL-7F-J|FJ|L7L-7L--J||||FJF7FJ||FJ|||FLJ|||L7FJF-7|||LJF7F----JLJ|L7||L-7|L7J7LFF|L7J|L|7.-JJF|--7
                LLJJ-J|LFJ7F7|LLJ||F7F-JL-J|L-7L7|L7L7|||L7F-JL7FJL7L-JF7L--7-||||L7||L7LJL7|LJF7.|||FJ|FJJLJLJF-JLJF-7F7F7L7||L7FJL7|.L-7.J...JF||7LF--.||.
                F|7|JF7.LFF-L|-F-JLJ|L7F7F7|F-JFJ|FJF||LJFJL7F7||F7|F7FJL7F7|FJ|LJF||L-JF--JL--J|FJ|LJF|L-7F--7|F7F-JFJ|LJ|FJ|L7LJLFJL-7J|-|-.FLF-7-7L7JF-.F
                FL|L-7L77F|7.LJL---7L-J|||LJL7FJ-||-FJ|F-JF7LJ|LJ||LJ|L7FJ|||L7||F7|L--7L--7F--7|L7|JF7|F-JL-7LJ||L7-|FJF-J|7L7|F7.L-7FJ-L.|JFJ-|FJ.L7L.F.FJ
                F7L7L7.L-L.F|JJF|LFJF7FJ|L7LFJL-7||FJFJL-7|L7FJF-J|F-JFJL7|LJFJL-J|L7F-JF--JL-7||L|L7|||L7F7FJF7|L7L7||FJF7L-7|LJL7J-LJ7|F-L.F|FJ7-L7F|7.F|F
                LL.|JL7|L|7LJJ||LLL7||L7|FJ.L7F-J||L7|-F-JL7||JL-7LJF7L-7|L-7|F7F7|FJL-7L-7F7FJ|L7L7LJ||FJ|LJFJLJFJFJ|||FJ|F7|L-7FJ--|.7|F-J-JFJLF---LFJ7FJJ
                FJ7|FLF7|FL7LFFJ.F.LJ|FJLJJFFJL-7LJ|LJ-L-7FJLJF--JF7|L7FJ|F7||||||||F--JF-J||L7|FJFJF7|||FJF7L-7LL-JFJ||L7LJ|L-7|L7J.77J|7|.LF7J.L.LFLJ.FJL7
                |JLF7FFJJ--7F-|-..FLFJ|J...FJF--J|L7.LF-FJ|.LFJF--J||FJ|FJ|LJ||LJLJ|L-7FJF-J|FJ|L7|FJ|||||FJL-7|.F||L7|L-J|FJF7|L7L--77-|-FJ-FL7|J7.7|..|F7F
                |L|.|L7JJF|LF7J-J7|LL-JFJ-.|FJFJLL7L7-LFL7|.FL7|F--J||FJL7|-L|||J-LL7FJL7|F-JL7L7||L7||||LJJ.LLJ-FJ|LLJ7F--JFJ|L7L7F-JJ||L|L-|F|-.77F--.7.|7
                L7LFF-FJF-F7L|7LLJJ7LJJ|JFFLJJ|-LFLJJJ7L|LJJLFLJL7F7|LJJFLJ||LJ7.FLFJ|FLLJ|F7FJ-|||FJ||LJ|LL7|L7|L|J.F--JF-7|FJFJ||L-7F|.FL7.F-.J.LJJF|-J.||
                FF7.JJL-J7|7L-J7J7F||.F|.-F.|FJF--|77-LL7J|.FFF--J|||F7|F|7LJ-||F-7L-JFF--J||L-7|||L7LJ.|||.|7|-LFL.FJF--J.||L7L7FJF-J7J.|JLF--7.|L|.F|.FL|7
                ----L7J7LFL77.L7-JJ|77FFJ-F7|FLJ7LF-L7|JL7L--F|F7FJ|LJL---7JLFLJJLL7F--JF7FJL7FJLJL-J7|7LF7F|JL-7|FFL-JJ7LL||L|FJ|FJJ|L7.7.F|7JJ-F--LJ.FFJLL
                L7L-7J-7F-7FL--JJ.LF.FFL-|L-|-JF7LLLJ|L--JJFFJLJLJ-|F-7F7FJ.L-7J|LLLL---J||7L||LL7F|J|LLJL|----L|||J7J-LJ.L||.|L7|||FFJFJ-|-7-----77L77|...|
                |F7JLJJF|--|||.J-7-J--7JFJ7.-7LJ77||-LFJ-7--||L|J7.|L7LJ||--J|F.|..|L|JF|LJ7L||-7LLJF7-L77L-J-77F|||7JFFFJ7LJ-L-JLJF-|F|F||..|-..F-7-||L----
                FJ||LLF-77.-|LF|-L.||LL.7JFL.F-L-7-L-.L.F-FJ.FFJJF-L-JJFJL7JFFL-|-L-7|.F7FJJ-LJ7L77.L-F-LL7JF--||LJJ--F-7L|JLLL-FJJF-|-F|-.-LL-77|.7.J-|..||
                FL--7.LLJ-JL7-FFJLF-J-|-F-7J-F.L|.|LJ---L-L7.-FJJL7JL..L--J-|L|.7--L|L-FJ|JJ-L.L-L-F-L|-LL7-JJ.LLFJ-|LLJLJ.JJLJ77J--.-.LL--JLJL|-LLJ7JL-J.-7""";
        //endregion DATA
    }

    public enum ConnectorType {
        CONNECTOR_NORTH_SOUTH("|", List.of(NORTH, SOUTH)),
        CONNECTOR_WEST_EAST("-", List.of(WEST, EAST)),
        CONNECTOR_NORTH_EAST("L", List.of(NORTH, EAST)),
        CONNECTOR_NORTH_WEST("J", List.of(NORTH, WEST)),
        CONNECTOR_SOUTH_WEST("7", List.of(SOUTH, WEST)),
        CONNECTOR_SOUTH_EAST("F", List.of(SOUTH, EAST)),
        CONNECTOR_TERMINUS("S", List.of(NORTH, WEST, EAST, SOUTH)),
        CONNECTOR_GROUND(".", List.of());

        private final String value;
        private final List<Direction> pointsTo;

        public String getValue() {
            return value;
        }

        public List<Direction> getPointsTo() {
            return pointsTo;
        }

        ConnectorType(String value, List<Direction> pointsTo) {
            this.value = value;
            this.pointsTo = pointsTo;
        }

        public static ConnectorType fromString(String symbol) {
            return Arrays.stream(values())
                    .parallel()
                    .filter(connectorType -> connectorType.getValue().equals(symbol))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No ConnectorType found for symbol: " + symbol));
        }

        @Override
        public String toString() {
            return name() + " (\"" + value + "\")";
        }
    }

    public static class Tile {

        final ConnectorType connectorType;
        final Coordinate coordinate;
        boolean isPerimeter;

        public Tile(String connector, int row, int column) {
            this.connectorType = fromString(connector);
            this.coordinate = new Coordinate(row, column);
        }

        public ConnectorType getConnectorType() {
            return connectorType;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public boolean getIsCounted() {
            return isPerimeter;
        }

        @Override
        public String toString() {
            String tileString =  "Tile '" + connectorType.getValue() +
                    "', (" + coordinate.row() + "," + coordinate.column() + ")";
            if (isPerimeter) {
                tileString = tileString + " - on perimeter";
            }
            return tileString;
        }
    }

    public static class TileGrid extends Grid<Tile> {
        /** Making height and width updated on put shaved another 3 seconds compared to the other TileGrid
         * after having made height() and width() multithreaded.
         * Initially, part 2 took 19 seconds.
         * Parallel isInsidePerimeter, 13 seconds
         * Parallel getNextUnconnectedTile, 9 seconds
         * Parallel height() and width(), 5 seconds
         * Calculated height and width on Put instead of on demand, 3 seconds
         * Threads for getting Perimeter added 1 second, so I abandoned it
         * Adding the isInBoundingBox, 2.5 seconds
         * Making Perimeter an ArrayList instead of TreeMap<Integer, Coordinate>, 1 second (thought I needed the TreeMap to maintain order)
         */

        public Tile getAdjacentTile(Tile tile, Direction direction) {

            Coordinate adjacentCoordinate = this.getAdjacentCoordinate(tile.getCoordinate(), direction);

            return adjacentCoordinate == null ? null : this.get(adjacentCoordinate);
        }

        public Tile getNextConnectedTile(Tile tile, Direction direction) {
            if (tile == null || direction == null) {
                throw new IllegalArgumentException("Tile and direction must not be null");
            }

            Tile adjacentTile = getAdjacentTile(tile, direction);

            if (adjacentTile != null && !adjacentTile.isPerimeter) {
                boolean areConnected = isPointedTo(tile, direction) && isPointedTo(adjacentTile, direction.getOpposite());
                return areConnected ? adjacentTile : null;
            }

            return null;
        }

        private boolean isPointedTo(Tile tile, Direction direction) {
            return tile.getConnectorType().getPointsTo().contains(direction);
        }
    }

    private static TileGrid getTiles(String dataStr) {
        TileGrid tileGrid = new TileGrid();
        String[] data = dataStr.split("\n");
        int rowCount = data.length;
        int columnCount = data[0].split("").length;

        int i = 0;
        do {
            String row = data[i];
            String[] tiles = row.split("");
            int j = 0;
            do {
                String value = tiles[j];
                tileGrid.put(i, j, new Tile(value, i, j));
                j++;
            } while (j < columnCount);
            i++;
        } while (i < rowCount);
        return tileGrid;
    }

    private static Coordinate getStartCoordinate(TileGrid tileGrid) {
        return tileGrid.values()
                .parallelStream()
                .filter(tile -> tile.getConnectorType().equals(CONNECTOR_TERMINUS))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Starting position missing from data."))
                .getCoordinate();
    }

    private static List<Coordinate> identifyPerimeterTiles(TileGrid tileGrid) {
        List<Coordinate> perimeter = new ArrayList<>();
        Coordinate startCoordinate = getStartCoordinate(tileGrid);
        Coordinate coordinate = startCoordinate;

        do {
            perimeter.add(coordinate);
            Tile tile = tileGrid.get(coordinate);
            tile.isPerimeter = true;

            Direction nextConnectedDirection = tile.getConnectorType().getPointsTo()
                    .parallelStream()
                    .filter(direction -> tileGrid.getNextConnectedTile(tile, direction) != null)
                    .findFirst()
                    .orElse(null);

            coordinate = nextConnectedDirection != null ?
                    coordinate.move(nextConnectedDirection) :
                    startCoordinate;
        } while (!Objects.equals(coordinate, startCoordinate));
        return perimeter;
    }

    private static boolean isInsideBoundingBox(List<Coordinate> perimeter, Coordinate coordinate) {
        long minX = Integer.MAX_VALUE;
        long minY = Integer.MAX_VALUE;
        long maxX = Integer.MIN_VALUE;
        long maxY = Integer.MIN_VALUE;

        // Find minimum and maximum coordinates of the bounding box
        for (Coordinate point : perimeter) {
            minX = Math.min(minX, point.getColumn());
            minY = Math.min(minY, point.getRow());
            maxX = Math.max(maxX, point.getColumn());
            maxY = Math.max(maxY, point.getRow());
        }

        // Check if the point falls within the bounding box
        return coordinate.getColumn() >= minX && coordinate.getColumn() <= maxX &&
                coordinate.getRow() >= minY && coordinate.getRow() <= maxY;
    }

    private static boolean isInsidePerimeter(List<Coordinate> perimeter, Coordinate coordinate) {
        int intersections = 0;
        for (int i = 0; i < perimeter.size(); i++) {
            Coordinate p1 = perimeter.get(i);
            Coordinate p2 = perimeter.get((i + 1) % perimeter.size());

            // Check if the coordinate lies on the edge of the polygon
            if (coordinate.getColumn() == p1.getColumn() && coordinate.getColumn() == p2.getColumn() &&
                    (coordinate.getRow() - p1.getRow()) * (coordinate.getRow() - p2.getRow()) <= 0) {
                return true;
            }

            // Check for intersection with the ray
            if ((p1.getColumn() > coordinate.getColumn()) != (p2.getColumn() > coordinate.getColumn()) &&
                    coordinate.getRow() < (p2.getRow() - p1.getRow()) * (coordinate.getColumn() - p1.getColumn()) / (p2.getColumn() - p1.getColumn()) + p1.getRow()) {
                intersections++;
            }
        }
        // If number of intersections is odd, coordinate is inside the polygon
        return intersections % 2 == 1;
    }

    private static TileGrid tileGrid = new TileGrid();
    private static long actualResult;
    private static long startTime;
    private static List<Coordinate> perimeter = new ArrayList<>();

    public static void main(Object... args) {
        int part = Integer.parseInt(String.valueOf(args[0]));
        boolean isDemo = args.length > 1 && args[1].equals(true);
        Map<String, Integer> dataToExpectedResult;

        startTime = System.nanoTime();

        if (part == 1) {

            if (isDemo) {
                dataToExpectedResult = Map.of(
                        PART_ONE_DEMO_DATA_SIMPLE_LOOP, PART_ONE_RESULT_DEMO_SIMPLE_LOOP,
                        PART_ONE_DEMO_DATA_SIMPLE_LOOP_PLUS, PART_ONE_RESULT_DEMO_SIMPLE_LOOP_PLUS,
                        PART_ONE_DEMO_DATA_IN_BETWEEN_LOOP, PART_ONE_RESULT_DEMO_IN_BETWEEN_LOOP,
                        PART_ONE_DEMO_DATA_COMPLEX_LOOP, PART_ONE_RESULT_DEMO_COMPLEX_LOOP
                );
            } else {
                dataToExpectedResult = Map.of(DATA, PART_ONE_RESULT);
            }

            dataToExpectedResult.forEach((dataStr, expectedResult) -> {
                tileGrid = getTiles(dataStr);

                perimeter = identifyPerimeterTiles(tileGrid);

                actualResult = perimeter.size() / 2;

                Utils.publishResult(startTime, expectedResult, actualResult);
            });
        }
        else {

            if (isDemo) {
                dataToExpectedResult = Map.of(
                        PART_TW0_DEMO_DATA_SIMPLE, PART_TWO_RESULT_DEMO_SIMPLE,
                        PART_TWO_DEMO_LESS_SIMPLE, PART_TWO_RESULT_DEMO_LESS_SIMPLE,
                        PART_TWO_DEMO_DATA_COMPLEX, PART_TWO_RESULT_DEMO_COMPLEX
                );
            } else {
                dataToExpectedResult = Map.of(DATA, PART_TWO_RESULT);
            }

            dataToExpectedResult.forEach((dataStr, expectedResult) -> {
                TileGrid tileGrid = getTiles(dataStr);

                perimeter = identifyPerimeterTiles(tileGrid);

                actualResult = tileGrid.entrySet()
                        .parallelStream()
                        .filter(entry -> !entry.getValue().getIsCounted())
                        .map(Map.Entry::getKey)
                        .filter(coordinate -> isInsideBoundingBox(perimeter, coordinate))
                        .filter(coordinate -> isInsidePerimeter(perimeter, coordinate))
                        .count();

                Utils.publishResult(startTime, expectedResult, actualResult);
            });
        }
    }
}

