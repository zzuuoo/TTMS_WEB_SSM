$(function () {
    to_page(1);
})

function to_page(pn) {
    $.ajax({
        url:"/getAllPlay",
        data:"pn="+ pn,
        type:"GET",
        success:function (result) {
            build_play_view(result);
            build_page_info(result);
            build_page_nav(result);
        }
    });
}

function build_play_view(result) {
    $("#play_table").empty();
    var plays = result.data.pageinfo.list;
    $.each(plays,function (index,item) {


        var name = $("<h3></h3>").append(item.playName);
        var introduction = $("<p></p>").append(item.playType + "," + item.playLang + "," + item.playLength + "分钟," + item.playTicketPrice + "元。" );

        var insertpicbtn = $("<button></button>").addClass("btn btn-success btn-sm")
            .append("更改图片").click(function () {

                $("#playupdateimagesModel").modal({
                    backdrop:'static'
                });
                
                // $("#play_updateimages_submit_btn").click(function () {
                //     var imagesfile = new FormData($("#play_updateimages_form")[0]);
                //     // alert(imagesfile);
                //         $.ajax({
                //             url:"/updateimages",
                //             data:imagesfile,
                //             type:"POST",
                //             success:function (result) {
                //                 if(result.msg=="success"){
                //                     alert("上传成功");
                //                 }
                //                 window.location.reload();
                //             }
                //         });
                //
                //
                //     });
                
            });

        var updatebtn = $("<button></button>").addClass("btn btn-primary btn-sm")
            .append("编辑").click(function () {
                $("#playUpdateModel").modal({
                    backdrop:'static'
                });

                $("#playId_update_input").val(item.playId);
                $("#playName_update_input").val(item.playName);
                $("#playIntroduction_update_input").val(item.playIntroduction);
                $("#playLength_update_input").val(item.playLength);
                $("#playTicketPrice_update_input").val(item.playTicketPrice);


                //修改提交
                // $("#play_submit_btn").click(function () {
                //     $.ajax({
                //         url:"/updatePlay",
                //         data:$("#play_Update_form").serialize(),
                //         type:"POST",
                //         success:function (result) {
                //             if(result.msg=="fail"){
                //                 alert("添加失败");
                //             }
                //             window.location.reload();
                //         }
                //     });
                // });

            });

        var deletebtn = $("<button></button>").addClass("btn btn-danger btn-sm")
            .append("删除").click(function () {

                //点击删除事件
                $("#playDeleteModel").modal({
                    backdrop:'static'
                });
                // alert(item.empId);
                $("#model_delete_btn").click(function () {
                    play_model_delete(result,item);
                });
            });
        var btnTd = $("<th></th>").append(updatebtn).append(" ").append(deletebtn);

        var captiondiv = $("<div></div>").append(name).append(introduction).append(btnTd);

        var img = $("<img src='" + item.playImage + "'>");

        var big_table = $("<div></div>").addClass("col-sm-6 col-md-3")
            .append("<div></div>").addClass("thumbnail")
            .append(img)
            .append(captiondiv);

        $(big_table).appendTo("#play_table");

    })
}

//模态框中点击删除
function play_model_delete(info,item) {
    $.ajax({
        url:"/deletePlayById",
        data:"playId="+ item.playId,
        type:"GET",
        success:function (result) {
            if(result.msg=="success"){
                alert("删除成功");
            }
            // to_page(info.data.pageinfo.pageNum);
            window.location.reload();
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
//添加model  影片添加
$("#play_add_model_btn").click(function () {
    $("#playAddModel").modal({
        backdrop:'static'
    });
});

//添加提交
// $("#play_insert_submit_btn").click(function () {
//     $.ajax({
//         url:"/insertPlay",
//         data:$("#play_Insert_form").serialize(),
//         type:"POST",
//         success:function (result) {
//             if(result.msg=="fail"){
//                 alert("添加失败");
//             }
//                 window.location.reload();
//
//         }
//     });
// });


