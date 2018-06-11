package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiyou.dao.SaleMapper;
import xiyou.pojo.Sale;
import xiyou.service.SaleService;

import java.util.List;
@Service
public class SaleServiceImpl implements SaleService{
    @Autowired
    private SaleMapper saleMapper;

    @Override
    public int insert(Sale sale) {
        return saleMapper.insert(sale);
    }

    @Override
    public int delete(Long saleId) {
        return saleMapper.deleteByPrimaryKey(saleId);
    }

    @Override
    public int update(Sale sale) {
        return saleMapper.updateByPrimaryKeySelective(sale);
    }

    @Override
    public List<Sale> selectByEmpId(Integer EmpId) {
        return saleMapper.selectByEmpId(EmpId);
    }

    @Override
    public List<Sale> selectAll() {
        return saleMapper.selectByExample(null);
    }

    @Override
    public Sale selectById(Long saleId) {
        return saleMapper.selectByPrimaryKey(saleId);
    }
}
