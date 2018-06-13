package xiyou.mobileController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xiyou.pojo.Sale;
import xiyou.pojo.SaleItem;
import xiyou.service.SaleService;

import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/mobileSale")
public class MobileSaleController {

    @Autowired
    private SaleService saleService;
    @ResponseBody
    @RequestMapping(value = "/getSale",method = RequestMethod.GET)
    public List<Sale> getSale()
    {
        return saleService.selectAll();
    }

    @ResponseBody
    @RequestMapping(value = "insertSale",method = RequestMethod.POST)
    public String insertSale(@RequestBody Sale sale)
    {
        if(saleService.insert(sale)>0)
        {
            return "succeed";
        }
        return "failed";
    }
    @ResponseBody
    @RequestMapping(value = "/getSaleByEmpId",method = RequestMethod.GET)
    public List<Sale> getSaleByEmpId(@RequestParam Integer empId)
    {
        return saleService.selectByEmpId(empId);
    }



}
