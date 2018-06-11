package xiyou.service;

import xiyou.pojo.Sale;

import java.util.List;

public interface SaleService {
    public int insert(Sale sale);
    public int delete(Long saleId);
    public int update(Sale sale);
    public List<Sale> selectByEmpId(Integer EmpId);
    public List<Sale> selectAll();
    public Sale selectById(Long saleId);
}
