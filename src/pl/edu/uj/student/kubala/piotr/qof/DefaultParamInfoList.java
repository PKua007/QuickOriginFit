// QuickOriginFit - DefaultParamInfoList.java
//---------------------------------------------------------------------
// [opis pliku]
//---------------------------------------------------------------------
// Utworzono 22:44 24.10.2017 w IntelliJ IDEA
// (C)PKua, wszystkie prawa zastrzeżone
//---------------------------------------------------------------------

package pl.edu.uj.student.kubala.piotr.qof;

import java.util.ArrayList;

public class DefaultParamInfoList implements ParamInfoList {

    private ArrayList<ParamInfo> infos = new ArrayList<>();

    @Override
    public void addParamInfo(ParamInfo info) {
        if (getParamInfoIdx(info) != -1)
            throw new IllegalArgumentException(info + " already in list");
        infos.add(info);
    }

    @Override
    public ParamInfo getParamInfo(int i) {
        return infos.get(i);
    }

    @Override
    public int getNumOfParamsInfos() {
        return infos.size();
    }

    @Override
    public int deleteParamInfo(int i) {
        infos.remove(i);
        return getNumOfParamsInfos();
    }

    @Override
    public int deleteParamInfo(ParamInfo info) {
        infos.remove(info);
        return getNumOfParamsInfos();
    }

    @Override
    public int getParamInfoIdx(ParamInfo info) {
        return infos.indexOf(info);
    }

    @Override
    public String toString() {
        return "DefaultParamInfoList{" +
                "infos=" + infos +
                '}';
    }
}
