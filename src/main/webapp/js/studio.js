//本地加载完毕之后加载
$(function () {
    to_page(1);
});

function to_page(pn) {
    $.ajax({
        url:"/getAllStudio",
        data:"pn="+ pn,
        type:"GET",
        success:function (result) {
            // console.log(result);
            // 解析并显示员工数据
            build_studio_table(result);
            // 解析并显示分页数据
            build_page_info(result);
            ///页码数据
            build_page_nav(result);
        }
    });
}


//表格数据展示
function build_studio_table(result) {
    //位置  #emp_table tbody
    //构建前清空表格
    $("#studio_table tbody").empty();
    var studios = result.data.pageinfo.list;
    $.each(studios,function (index,item) {
        // alert(item.empName);
        var studioIdId = $("<td></td>").append(item.studioId);
        var studioNameId =  $("<td></td>").append(item.studioName);
        var studioRowCountId = $("<td></td>").append(item.studioRowCount);
        var studioColCountId = $("<td></td>").append(item.studioColCount);
        var studioFlagId = $("<td></td>").append(item.studioFlag);
        //加入button
        var updatebtn = $("<button></button>").addClass("btn btn-primary btn-sm")
            .append("编辑").click(function () {


                $("#studioUpdateModel").modal({
                    backdrop:'static'
                });

                //给编辑模态框中添加数值

                $("#studioId_Update_input").attr('value',item.studioId);

                $("#studioName_Update_input").attr('value',item.studioName);
                $("#studioRowCount_Update_input").attr('value',item.studioRowCount);
                $("#studioColCount_Update_input").attr('value',item.studioColCount);
                $("#studioFlag_Update_input").attr('value',item.studioFlag);

                //提交修改按钮事件
                $("#studio_submit_btn").click(function (){
                    studio_model_Update_btn(result);

                });
            });
        var deletebtn =  $("<button></button>").addClass("btn btn-danger btn-sm")
            .append("删除").click(function () {
                //点击删除事件

                $("#studioDeleteModel").modal({
                    backdrop:'static'
                });
                // alert(item.empId);
                $("#model_delete_btn").click(function () {
                    studio_model_delete(result);
                });
            });
        var btnTd = $("<th></th>").append(updatebtn).append(" ").append(deletebtn);
        $("<tr></tr>").append(studioIdId)
            .append(studioNameId)
            .append(studioRowCountId)
            .append(studioColCountId)
            .append(studioFlagId)
            .append(btnTd)
            .appendTo("#studio_table tbody");
    })
}

//修改员工信息
function studio_model_Update_btn(info) {
    $.ajax({
        url:"/updateStudioById",
        data: $("#studio_Update_form").serializeArray(),
        type:"POST",

        success:function () {

            window.location.reload();
        }
    });
}



//模态框中点击删除
function studio_model_delete(info,item) {
    $.ajax({
        url:"/deleteStudioById",
        data:"studio_id="+ item.studioId,
        type:"GET",
        success:function (result) {

            to_page(info.data.pageinfo.pageNum);
        }
    });
}



//分页信息展示
function build_page_info(result) {
    //位置  #page_info_area
    //清空 表格数据
    $("#page_info_area").empty();
    var info = result.data.pageinfo;
    var pagenum = info.pageNum;
    var total = info.total;
    $("#page_info_area").append("当前第"+pagenum+"页，总共"+total+"条数据");
}

//导航栏信息
function build_page_nav(result) {
    //page_nav_area区域
    //清空表格
    $("#page_nav_area").empty();
    var ul = $("<ul></ul>").addClass("pagination")
    var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
    var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
    //判断前一页是否可点击
    if(result.data.pageinfo.hasPreviousPage == false){
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    }else {
        //添加点击翻页事件
        firstPageLi.click(function () {
            to_page(1);
        });
        prePageLi.click(function () {
            to_page(result.data.pageinfo.pageNum-1);
        });
    }



    //下一页  末页
    var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
    var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
    //判断是否有下一页，是否可点击
    if(result.data.pageinfo.hasNextPage == false){
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    }else {
        //添加点击翻页事件
        nextPageLi.click(function () {
            to_page(result.data.pageinfo.pageNum+1);
        });
        lastPageLi.click(function () {
            to_page(result.data.pageinfo.pages);
        });
    }

    ul.append(firstPageLi).append(prePageLi);
    $.each(result.data.pageinfo.navigatepageNums,function (index,item) {
        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if(result.data.pageinfo.pageNum == item){
            numLi.addClass("active");
        }
        numLi.click(function () {
            to_page(item);
        })
        ul.append(numLi);
    });
    ul.append(nextPageLi).append(lastPageLi);
    var navId = $("<nav></nav>").append(ul);
    navId.appendTo("#page_nav_area");
}

//弹出框
//添加model  员工添加
$("#studio_add_model_btn").click(function () {
    $("#studioAddModel").modal({
        backdrop:'static'
    });


});

$("#studio_insert_submit_btn").click(function(){
    // alert($("#emp_Insert_form").serialize());
    $.ajax({
        url:"/insertStudio",
        data: $("#studio_Insert_form").serialize(),
        type:"POST",
        success:function () {

            window.location.reload();
        }
    });
});

//将模态框中填写的数据提交服务器保存
// $("#emp_submit_btn").click(function () {
//     alert($("#empUpdateModel form").serialize());
// });

