package ru.ecom.diary.ejb.service.protocol;

import ru.ecom.diary.ejb.domain.protocol.parameter.Parameter;
import ru.ecom.diary.ejb.domain.protocol.parameter.ParameterByForm;
import ru.ecom.diary.ejb.domain.protocol.template.TemplateProtocol;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNode;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByGroup;
import ru.ecom.diary.ejb.service.protocol.tree.CheckNodeByParameter;
import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.ejb.services.util.ConvertSql;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.TreeSet;

/**
 * User: STkacheva
 * Протокол
 */
@Stateless
@Remote(IDiaryService.class)
public class DiaryServiceBean implements IDiaryService {

    @Override
    public CheckNode loadParametersByMedService(long aTemplateId) {
        return loadParametersByMedService(aTemplateId, "");
    }

    @Override
    public CheckNode loadParametersByMedService(long aTemplateId, String aUsername) {
        return loadParametersByMedService(aTemplateId, aUsername, "p.template_id");
    }

    @Override
    public CheckNode loadParametersByMedService(long aTemplateId, String aUsername, String aField) {
        TreeSet<Long> parametersSet = new TreeSet<>();
        List<Object> list = manager.createNativeQuery("select p.parameter_id from ParameterByForm p where " + aField + " =:temp order by p.position").setParameter("temp", aTemplateId).getResultList();
        for (Object param : list) {
            if (param != null) parametersSet.add(ConvertSql.parseLong(param));
        }

        List<Object[]> rootGroups = findRootGroups(aUsername);

        CheckNodeByGroup rootNode = new CheckNodeByGroup("g" + 0, "/", false);
        for (Object[] group : rootGroups) {
            Long id = ConvertSql.parseLong(group[0]);
            CheckNodeByGroup node = new CheckNodeByGroup("g" + id, "" + group[1], false);
            rootNode.getChilds().add(node);
            addGroups(id, node, parametersSet, aUsername);
            addParameters(id, node, parametersSet);
        }

        return rootNode;
    }

    private List<Object[]> findRootGroups(String aLogin) {
        return manager.createNativeQuery("select pg.id,pg.name from ParameterGroup pg" +
                " left join parametergroup_secgroup pgsg on pgsg.parametergroup_id=pg.id" +
                " left join secgroup_secuser sgsu on sgsu.secgroup_id=pgsg.secgroups_id" +
                " left join secuser su on su.id=sgsu.secusers_id" +
                " where pg.parent_id is null and su.login='" + aLogin + "' group by pg.id, pg.name order by pg.name").setMaxResults(100).getResultList();
    }

    private void addGroups(Long aGroup, CheckNode aNode, TreeSet<Long> aParameters, String aUsername) {
        List<Object[]> listChild = manager.createNativeQuery("select pg.id, pg.name from ParameterGroup pg " +
                " left join parametergroup_secgroup pgsg on pgsg.parametergroup_id=pg.id" +
                " left join secgroup_secuser sgsu on sgsu.secgroup_id=pgsg.secgroups_id" +
                " left join secuser su on su.id=sgsu.secusers_id" +
                " where pg.parent_id = :parent and su.login =:login group by pg.id, pg.name order by name").setParameter("parent", aGroup).setParameter("login", aUsername).getResultList();
        for (Object[] group : listChild) {
            Long id = ConvertSql.parseLong(group[0]);
            CheckNodeByGroup node = new CheckNodeByGroup(
                    "g" + group[0], String.valueOf(group[1]), false
            );
            aNode.getChilds().add(node);
            addGroups(id, node, aParameters, aUsername);
            addParameters(id, node, aParameters);
        }
    }

    private void addParameters(Long aGroup, CheckNode aNode, TreeSet<Long> aParameters) {
        List<Object[]> listChild = manager.createNativeQuery("select par.id,par.name||' ('||case when par.type='1' then 'Числовой' when par.type='4' then 'Числовой с плавающей точкой зн.'||par.cntDecimal when par.type='2' then 'Пользовательский справочник: '||coalesce(vd.name,'НЕ УКАЗАН!!!!!!!') when par.type='3' then 'Текстовое поле' when par.type='5' then 'Текстовое поле с ограничением' else 'неизвестный' end||') - '||coalesce(vmu.name,''),par.type as partype from Parameter par left join userDomain vd on vd.id=par.valueDomain_id left join vocMeasureUnit vmu on vmu.id=par.measureUnit_id where par.group_id = :parent order by par.name").setParameter("parent", aGroup).getResultList();

        for (Object[] param : listChild) {
            CheckNodeByParameter node = new CheckNodeByParameter("p" + param[0],
                    String.valueOf(param[1])
                    , aParameters.contains(ConvertSql.parseLong(param[0])), ConvertSql.parseLong(param[2]));
            aNode.getChilds().add(node);
        }
    }

    @Override
    public void saveParametersByTemplateProtocol(String aIdFieldName, long aProtocolId, long[] aAdded, long[] aRemoved) {


        if (aIdFieldName == null || aIdFieldName.equals("")) {
            aIdFieldName = "template_id";
        }
        int i = 0;
        for (long idParam : aAdded) {
            i++;
            List<ParameterByForm> list = manager.createQuery("from ParameterByForm where " + aIdFieldName + "='" + aProtocolId + "' and parameter_id='" + idParam + "'").getResultList();
            if (list.isEmpty()) {
                Parameter param = manager.find(Parameter.class, idParam);
                ParameterByForm frm = new ParameterByForm();
                frm.setParameter(param);
                if (aIdFieldName.equals("template_id")) {
                    TemplateProtocol protocol = manager.find(TemplateProtocol.class, aProtocolId);
                    frm.setTemplate(protocol);
                } else if (aIdFieldName.equals("assessmentCard")) {
                    frm.setAssessmentCard(aProtocolId);
                }
                frm.setPosition((long) i);
                manager.persist(frm);
            } else {
                ParameterByForm frm = list.get(0);
                frm.setPosition((long) i);
                manager.persist(frm);
                if (list.size() > 1) {
                    for (int j = 1; j < list.size(); j++) {
                        ParameterByForm fr = list.get(i);
                        manager.remove(fr);
                    }
                }

            }
        }
        for (long idParam : aRemoved) {
            manager.createNativeQuery("delete from ParameterByForm where " + aIdFieldName + "='" + aProtocolId + "' and parameter_id='" + idParam + "'").executeUpdate();
        }
    }

    @EJB
    ILocalEntityFormService entityFormService;
    @PersistenceContext
    EntityManager manager;
}
