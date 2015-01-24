package examtool.exam;

import examtool.model.Mark;

import java.util.List;

/**
 * Created by Olga Bolshakova (obolshakova@yandex-team.ru)
 * <p/>
 * 24.01.15 12:42
 */
public interface SessionMark {

    Mark currentMark();

    Mark nextMark(boolean isNextAnswerCorrect);

    Mark maxPossibleMark();

    Mark minPossibleMark();

    List<Boolean> getAnswersMask();

}
