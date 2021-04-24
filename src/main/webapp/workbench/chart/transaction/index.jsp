<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + "/" + request.getContextPath() + "/";
%>
<html>
    <head>
        <title></title>
        <base href="<%=basePath%>"/>
        <script src="ECharts/echarts.min.js"></script>
        <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
        <script type="text/javascript">

            $(function(){
                // echartsFunc(); //柱状图
                echartsFuncL()
            })

            function echartsFunc(){
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: 'ECharts 入门示例'
                    },
                    tooltip: {},
                    legend: {
                        data:['销量']
                    },
                    xAxis: {
                        data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
                    },
                    yAxis: {},
                    series: [{
                        name: '销量',
                        type: 'bar',
                        data: [5, 20, 36, 10, 10, 20]
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
            function echartsFuncL(){
                $.ajax({
                    url : "ssm/transaction/tranImage",
                    type : "get",
                    dataType : "json",
                    success : function(data){

                        var myChart = echarts.init(document.getElementById('main'));
                        var option = {
                            title: {
                                text: '交易漏斗图',
                                subtext: '统计交易漏斗图'
                            },
                            // tooltip: {
                            //     trigger: 'item',
                            //     formatter: "{a} <br/>{b} : {c}%"
                            // },
                            // toolbox: {
                            //     feature: {
                            //         dataView: {readOnly: false},
                            //         restore: {},
                            //         saveAsImage: {}
                            //     }
                            // },
                            // legend: {
                            //     data: ['展现','点击','访问','咨询','订单']
                            // },

                            series: [
                                {
                                    name:'漏斗图',
                                    type:'funnel',
                                    left: '10%',
                                    top: 60,
                                    //x2: 80,
                                    bottom: 60,
                                    width: '80%',
                                    // height: {totalHeight} - y - y2,
                                    min: 0,
                                    max: data.total,
                                    minSize: '0%',
                                    maxSize: '100%',
                                    sort: 'descending',
                                    gap: 2,
                                    label: {
                                        show: true,
                                        position: 'inside'
                                    },
                                    labelLine: {
                                        length: 10,
                                        lineStyle: {
                                            width: 1,
                                            type: 'solid'
                                        }
                                    },
                                    itemStyle: {
                                        borderColor: '#fff',
                                        borderWidth: 1
                                    },
                                    emphasis: {
                                        label: {
                                            fontSize: 20
                                        }
                                    },
                                    data: data.dataList
                                        // [
                                        // {value: 60, name: '访问'},
                                        // {value: 40, name: '咨询'},
                                        // {value: 20, name: '订单'},
                                        // {value: 80, name: '点击'},
                                        // {value: 100, name: '展现'}
                                        // ]

                                }
                            ]
                        };
                        myChart.setOption(option);
                    }
                })
            }
        </script>
    </head>
    <body>
        <div id="main" style="width: 600px;height:400px;"></div>
    </body>
</html>

