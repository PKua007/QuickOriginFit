// QuickOriginFit - ParamInfoList.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:44 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import java.util.List;

public interface ParamInfoList
{
    void addParamInfo(ParamInfo info);
    ParamInfo getParamInfo(int i);
    int getNumOfParamsInfos();
    int deleteParamInfo(int i);
    int deleteParamInfo(ParamInfo info);
    int getParamInfoIdx(ParamInfo info);
    List<ParamInfo> getAllInfos();
}
