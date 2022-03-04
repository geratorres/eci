package com.encora.eci.repositories;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private EntityManager em;
    private static List<String> ranges = new ArrayList<>();

    static {
        ranges.add("Under $10,000");
        ranges.add("Above 10,000 to 20,000");
        ranges.add("Above 20,000 to 30,000");
        ranges.add("Above 30,000 to 40,000");
        ranges.add("Above 40,000 to 50,000");
        ranges.add("Above 50,000 to 20,000");
        ranges.add("Above 60,000 to 70,000");
        ranges.add("Above 70,000 to 80,000");
        ranges.add("Above 80,000 to 90,000");
        ranges.add("Above 90,000 to 100,000");
        ranges.add("Above 100,000");
    }

    public ReportRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Map<String, Integer> getEmployeeCountPerPosition() {

        Query q = em.createNativeQuery(
                "SELECT " +
                        "    ep.position_name, COUNT(*) " +
                        "FROM " +
                        "    employee e " +
                        "LEFT JOIN " +
                        "    employee_position ep " +
                        "ON e.id_employee_position = ep.id " +
                        "GROUP BY ep.position_name");

        List<Object[]> resultList = q.getResultList();

        Map<String, Integer> report = new HashMap<>();


        for (Object[] row : resultList) {

            if (row[0] == null) row[0] = "Without Position";

            report.put((String) row[0], Integer.parseInt(String.valueOf(row[1])));
        }

        return report;
    }

    @Override
    public Map<String, Integer> getGenderCount() {
        Query q = em.createNativeQuery(
                "SELECT " +
                        "    e.gender, " +
                        "    COUNT(e.gender) " +
                        "FROM " +
                        "    employee e " +
                        "GROUP BY e.gender");


        List<Object[]> result = q.getResultList();

        Map<String, Integer> resultMap = new HashMap<>();

        for (Object[] row : result) {
            resultMap.put((String) row[0], Integer.parseInt(String.valueOf(row[1])));
        }

        return resultMap;
    }

    @Override
    public List<Object[]> getSalaryRanges() {
        Query q = em.createNativeQuery(
                "SELECT " +
                        "    SUM(CASE WHEN e.salary <= 10000 THEN 1 ELSE 0 END) as '" + ranges.get(0) + "', " +
                        "    SUM(CASE WHEN e.salary > 10000 AND e.salary <= 20000 THEN 1 ELSE 0 END) as '" + ranges.get(1) + "', " +
                        "    SUM(CASE WHEN e.salary > 20000 AND e.salary <= 30000 THEN 1 ELSE 0 END) as '" + ranges.get(2) + "', " +
                        "    SUM(CASE WHEN e.salary > 30000 AND e.salary <= 40000 THEN 1 ELSE 0 END) as '" + ranges.get(3) + "', " +
                        "    SUM(CASE WHEN e.salary > 40000 AND e.salary <= 50000 THEN 1 ELSE 0 END) as '" + ranges.get(4) + "', " +
                        "    SUM(CASE WHEN e.salary > 50000 AND e.salary <= 60000 THEN 1 ELSE 0 END) as '" + ranges.get(5) + "', " +
                        "    SUM(CASE WHEN e.salary > 60000 AND e.salary <= 70000 THEN 1 ELSE 0 END) as '" + ranges.get(6) + "', " +
                        "    SUM(CASE WHEN e.salary > 70000 AND e.salary <= 80000 THEN 1 ELSE 0 END) as '" + ranges.get(7) + "', " +
                        "    SUM(CASE WHEN e.salary > 80000 AND e.salary <= 90000 THEN 1 ELSE 0 END) as '" + ranges.get(8) + "', " +
                        "    SUM(CASE WHEN e.salary > 90000 AND e.salary <= 1000000 THEN 1 ELSE 0 END) as '" + ranges.get(9) + "', " +
                        "    SUM(CASE WHEN e.salary > 100000 THEN 1 ELSE 0 END) as '" + ranges.get(10) + "' " +
                        "FROM " +
                        "    employee e ");


        Object[] result = (Object[]) q.getSingleResult();

        List<Object[]> salaryRanges = new ArrayList<>(result.length);

        for (int i = 0; i < result.length; i++) {
            Object[] range = {ranges.get(i), Integer.parseInt(result[i].toString())};
            salaryRanges.add(range);
        }

        return salaryRanges;
    }

    @Override
    public Map<String, Map<String, Integer>> getEmployeesByLocation() {
        Query q = em.createNativeQuery(
                "SELECT " +
                        "    e.country, " +
                        "    e.state, " +
                        "    COUNT(*) " +
                        "FROM " +
                        "   employee e " +
                        "GROUP BY e.country, e.state");

        Map<String, Map<String, Integer>> report = new HashMap<>();

        q.getResultStream().forEach(e -> {
            Object[] row = (Object[]) e;

            if (!report.containsKey(row[0])) {
                report.put((String) row[0], new HashMap<>());
            }

            report.get(row[0]).put((String) row[1], Integer.parseInt(row[2].toString()));
        });

        return report;
    }
}
