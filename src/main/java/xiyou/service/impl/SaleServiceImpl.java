package xiyou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xiyou.dao.SaleItemMapper;
import xiyou.dao.SaleMapper;
import xiyou.pojo.Sale;
import xiyou.pojo.SaleItem;
import xiyou.service.SaleService;

import java.math.BigDecimal;
import java.util.List;
@Service
public class SaleServiceImpl implements SaleService{
    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private SaleItemMapper saleItemMapper;

    @Override
    public int insert(Sale sale) {
//                SaleItem saleItem = new SaleItem();
//                saleItem.setSaleId(sale.getSaleId());
//                saleItem.setSaleItemPrice(new BigDecimal(123));
//                saleItem.setTicketId(ticket.getTicketId());
        return saleMapper.insertSelective(sale);
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
