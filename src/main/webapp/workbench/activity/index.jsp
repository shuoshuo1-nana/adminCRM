<%@page pageEncoding="UTF-8" contentType="text/html; UTF-8" %>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="UTF-8">

    <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
          rel="stylesheet"/>

    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript"
            src="../../jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

    <link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
    <script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

    <script>


        <%--初始化网页加载--%>
        $(function () {
            <%--调用加载user姓名函数--%>
            selectusers();

            <%--调用添加市场活动函数--%>
            addActivity();

            <%--调用日历函数--%>
            time();

            <%--调用分页查询函数--%>
            pageList(1, 2);

            <%--显示市场活动信息--%>
            // showActivity($length);

            <%--复选框绑定全选事件--%>
            $("#qx").click(function () {
                $("input[name=xz]").prop("checked", this.checked)
            })

            $("#selects").on("click", $("input[name=xz]"), function () {
                $("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length)
            })
            <%--显示市场活动信息--%>
            $("#updateBtn").click(function () {
                //获取选中id   input[name=xz]:checked
                var $xz = $("input[name=xz]:checked");
                if ($xz.length == 0) {
                    alert("请选择一条数据作为修改");
                } else if ($xz.length > 1) {
                    alert("同时只能修改一条数据");
                } else {
                    var id = $xz.val();
                    $.ajax({
                        url: "workbench/Activity/showActivity.do",
                        type: "get",
                        data: {
                            "id": id
                        },
                        cache: false,
                        dataType: "json",
                        success: function (data) {
                            <%--
                            data
                            {"name":[{name},{name}],"activity":[{1},{2},{3}]}
                            --%>
                            var ida = "";
                            var html = "<option></option>";
                            $.each(data.name, function (i, n) {
                                html += "<option value=" + n.id + ">" + n.name + "</option>";
                                if (data.activity.owner == n.id) {
                                    ida = n.id
                                }
                            });

                            $("#edit-owner").html(html);
                            $("#edit-id").val(id);
                            $("#edit-name").val(data.activity.name);
                            $("#edit-startDate").val(data.activity.startDate);
                            $("#edit-endDate").val(data.activity.endDate);
                            $("#edit-cost").val(data.activity.cost);
                            $("#edit-description").val(data.activity.description);
                            $("#editActivityModal").modal("show");
                            $("#edit-owner").val(ida);
                        }
                    })
                }


            })

            <%--更新活动列表--%>
            $("#update").click(function () {
                $.ajax({
                    url: "workbench/Activity/update.do",
                    type: "post",
                    data: {
                        "id": $("#edit-id").val(),
                        "owner": $("#edit-owner").val(),
                        "name": $("#edit-name").val(),
                        "startDate": $("#edit-startDate").val(),
                        "edit-endDate": $("#edit-endDate").val(),
                        "cost": $("#edit-cost").val(),
                        "description": $("#edit-description").val()
                    },
                    success: function (data) {
                        if (data) {
                            $("#editActivityModal").modal("hide");
                            pageList(1, 2)
                        } else {
                            alert("更新错误请重试");
                        }
                    },
                    dataType: "json"
                })
            })

            <%--删除信息--%>
            $("#deleteBtn").click(function () {
                var $xz = $("input[name=xz]:checked");
                if ($xz.length == 0) {
                    alert("请选择要删除的内容");
                } else {
                    if (confirm("您确定要删除吗")) {

                        var param = "";
                        for (var i = 0; i < $xz.length; i++) {
                            param += "id=" + $($xz[i]).val();
                            if (i < $xz.length - 1) {
                                param += "&";
                            }
                        }
                        $.ajax({
                            url: "workbench/Activity/delete.do",
                            type: "post",
                            data: param,
                            success: function (data) {
                                if (data) {
                                    pageList(1, 2);
                                } else {
                                    alert("删除失败，请重试");
                                }
                            }
                        })

                    } else {

                    }
                }
            })

        })
        <%--日历插件--%>

        function time() {
            <%--日历系统--%>
            $(".time").datetimepicker({
                language: "zh-CN",
                format: "yyyy-mm-dd",//显示格式
                minView: "hour",//设置只显示到月份
                initialDate: new Date(),//初始化当前日期
                autoclose: true,//选中自动关闭
                todayBtn: true, //显示今日按钮
                clearBtn: true,
                pickerPosition: "bottom-left"
            });
        }

        <%-- 加载user表里面的姓名 --%>

        function selectusers() {
            $("#selectusers").click(function () {
                $.ajax({
                    url: "workbench/Activity/Activity.do",
                    type: "get",
                    cache: false,
                    dataType: "json",
                    success: function (data) {
                        <%--

                        json   [{user},{user},{user},{user}]
                         <option>zhangsan</option>
                                      <option>lisi</option>
                                      <option>wangwu</option>
                        --%>
                        var html = "<option></option>";
                        $.each(data, function (i, n) {
                            html += "<option value=" + n.id + ">" + n.name + "</option>";
                        });
                        $("#create-owner").html(html);
                        $("#createActivityModal").modal("show");
                        var id = "${user.id}";
                        $("#create-owner").val(id)
                    }
                })
            })

        }

        <%--添加市场活动信息--%>

        function addActivity() {
            $("#addActivity").click(function () {
                $.ajax({
                    url: "workbench/Activity/AddActivity.do",
                    data: {
                        "owner": $.trim($("#create-owner").val()),
                        "name": $.trim($("#create-name").val()),
                        "startDate": $.trim($("#create-startDate").val()),
                        "endDate": $.trim($("#create-endDate").val()),
                        "cost": $.trim($("#create-cost").val()),
                        "description": $.trim($("#create-description").val())
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            $("#saveForm")[0].reset();
                        } else {
                            alert("创建失败");
                        }
                    }
                })

                $("#createActivityModal").modal("hide");
                pageList(1, 2);
            })
        }

        <%--分页查询--%>

        function pageList(pageNo, pageSize) {
            $.ajax({
                url: "workbench/Activity/selectActivity.do",
                type: "post",
                data: {
                    "name": $.trim($("#select-name").val()),
                    "owner": $.trim($("#select-owner").val()),
                    "startDate": $.trim($("#select-startDate").val()),
                    "endDate": $.trim($("#select-endDate").val()),
                    "pageNo": pageNo,
                    "pageSize": pageSize
                },
                cache: false,
                dataType: "json",
                success: function (data) {
                    <%--
                    data
                      ['total':100,'activlity':{activlity[1]},{2},{3}]
                    --%>
                    var html = "";
                    $.each(data.dataList, function (i, n) {
                        html += '<tr class="active">';
                        html += '<tr class="active">';
                        html += '<td><input type="checkbox" name="xz" value="' + n.id + '"/></td>';
                        html += '<td><a style="text-decoration: none; cursor: pointer;"';
                        html += 'onclick="window.location.href=workbench/activity/detail.jsp;">' + n.name + '</a></td>';
                        html += '<td>' + n.owner + '</td>';
                        html += '<td>' + n.startDate + '</td>';
                        html += '<td>' + n.endDate + '</td>';
                    })

                    $("#selects").html(html);

                    var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;

                    $("#activityPage").bs_pagination({
                        currentPage: pageNo, // 页码
                        rowsPerPage: pageSize, // 每页显示的记录条数
                        maxRowsPerPage: 20, // 每页最多显示的记录条数
                        totalPages: totalPages, // 总页数
                        totalRows: data.total, // 总记录条数

                        visiblePageLinks: 3, // 显示几个卡片

                        showGoToPage: true,
                        showRowsPerPage: true,
                        showRowsInfo: true,
                        showRowsDefaultInfo: true,

                        /*

                            该函数是在我们点击分页组件的时候，触发的

                         */
                        onChangePage: function (event, data) {
                            pageList(data.currentPage, data.rowsPerPage);
                        }
                    });
                }

            })
        }


    </script>
</head>
<body>

<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form" id="saveForm">

                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-owner">

                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-startDate">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control time" id="create-endDate">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="addActivity">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" role="form">
                    <span id="edit-id"></span>
                    <div class="form-group">
                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="edit-owner">
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-startDate">
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-endDate">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="edit-cost">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="edit-description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="update">更新</button>
            </div>
        </div>
    </div>
</div>


<div>
    <div style="position: relative; left: 10px; top: -10px;">
        <div class="page-header">
            <h3>市场活动列表</h3>
        </div>
    </div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
    <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input class="form-control" type="text" id="select-name">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input class="form-control" type="text" id="select-owner">
                    </div>
                </div>


                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input class="form-control time" type="text" id="select-startDate"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input class="form-control time" type="text" id="select-endDate">
                    </div>
                </div>

                <button type="submit" class="btn btn-default">查询</button>

            </form>
        </div>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" id="selectusers"><span
                        class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" id="updateBtn" data-target="#editActivityModal"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" id="deleteBtn"><span
                        class="glyphicon glyphicon-minus"></span> 删除
                </button>
            </div>

        </div>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover" id="">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="qx"/></td>
                    <td>名称</td>
                    <td>所有者</td>
                    <td>开始日期</td>
                    <td>结束日期</td>
                </tr>
                </thead>
                <tbody id="selects">
                </tr>
                </tbody>
            </table>
        </div>

        <div style="height: 50px; position: relative;top: 30px;">

            <div id="activityPage"></div>

        </div>

    </div>

</div>
</body>
</html>