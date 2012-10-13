package org.codeswarm.orafile;

import java.util.*;

public class OraNamedParamList extends OraParam {

    final Map<String, List<OraParam>> map = new HashMap<String, List<OraParam>>();
    final List<OraNamedParam> list = new ArrayList<OraNamedParam>();

    public OraNamedParamList() {}

    public OraNamedParamList(OraNamedParam namedParam) {
        if (namedParam == null) {
            throw new NullPointerException();
        }
        add(namedParam);
    }

    public void add(OraNamedParam namedParam) {
        String name = namedParam.getName();
        {
            List<OraParam> list = map.get(name);
            if (list == null) {
                list = new ArrayList<OraParam>();
                map.put(name, list);
            }
            list.add(namedParam.getParam());
        }
        list.add(namedParam);
    }

    public void add(OraNamedParamList namedParamList) {
        for (OraNamedParam namedParam : namedParamList.list) {
            add(namedParam);
        }
    }

    public List<OraNamedParam> asList() {
        return Collections.unmodifiableList(list);
    }

    public List<OraParam> get(String name) {
        List<OraParam> params = map.get(name.toUpperCase());
        if (params != null) {
            return params;
        } else {
            return Arrays.asList(new OraParam[]{});
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (OraNamedParam namedParam : list) {
            sb.append(namedParam.getName());
            String v = namedParam.getParam().toString();
            if (v.contains("\n")) {
                for (String line : v.split("\n")) {
                    sb.append("\n  ").append(line);
                }
            } else {
                sb.append(": ").append(v);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OraNamedParamList that = (OraNamedParamList) o;
        return list.equals(that.list);
    }

    public int hashCode() {
        return list.hashCode();
    }

    public OraNamedParamList asNamedParamList() {
        return this;
    }

}
