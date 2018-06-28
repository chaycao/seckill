var seckill = {
    // 封装秒杀相关ajax的url
    URL : {
        now : function() {
            return '/seckill/time/now';
        },
        expose : function (seckillId) {
            return '/seckill/'+seckillId+'/exposer';
        },
        execution : function (seckillId, md5) {
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },
    handleSeckillkill : function (seckillId, node) {
        // 获取秒杀地址，处理秒杀逻辑
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        $.post(seckill.URL.expose(seckillId), {}, function (result) {
            // 回调函数中，执行交互
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    // 已开启秒杀
                    // 获取秒杀地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log("killUrl:"+killUrl);
                    // 绑定一次点击事件，防止连续点击
                    $("#killBtn").one('click', function() {
                        // 1. 禁用按钮
                        $(this).addClass('disabled');
                        // 2. 发送秒杀请求
                        $.post(killUrl, {}, function(result) {
                           if (result && result['success']) {
                               var killResult = result['data'];
                               var state = killResult['state'];
                               var stateInfo = killResult['stateInfo'];
                               // 3. 显示秒杀结果
                               node.html('<span class="label label-success">'+stateInfo+'</span>');
                           }
                        });
                    });
                    node.show();
                } else {
                    // 未开启秒杀
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    // 重新计算计时逻辑
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                consolo.log('result:' + result);
            }
        });

    },
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        console.log('nowTime='+nowTime); //TODO
        console.log('startTime='+startTime); //TODO
        console.log('endTime='+endTime); //TODO
        if (nowTime > endTime) {
            // 秒杀结束
            seckillBox.html('秒杀结束！');
        } else if (nowTime < startTime) {
            // 秒杀未开始，计时事件绑定
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                // 时间格式
                var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                // 倒计时完成后回调事件
                // 获取秒杀地址，执行秒杀
                seckill.handleSeckillkill(seckillId, seckillBox);
            });
        } else {
            // 秒杀开始
            seckill.handleSeckillkill(seckillId, seckillBox);
        }
    },
    // 详情页秒杀逻辑
    detail : {
        // 详情页初始化
        init : function (params) {
            // 用户手机验证和登录， 计时交互
            // 在cookie中查找手机号
            var killPhone = $.cookie("killPhone");
            // 验证手机号
            if (!seckill.validatePhone(killPhone)) {
                // 绑定手机号
                var killPhoneModal = $('#killPhoneModal');
                // 显示弹出层
                killPhoneModal.modal({
                    show: true, // 显示弹出层
                    backdrop: 'static', // 禁止位置关闭
                    keyboard: false // 关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                   var inputPhone = $('#killPhoneKey').val();
                   if(seckill.validatePhone(inputPhone)) {
                       // 电话写入cookie
                       $.cookie('killPhone', inputPhone, {expires:7, path:'/seckill'});
                       // 刷新页面
                       window.location.reload();
                   } else {
                       $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
                   }
                });
            }
            // 已经登录
            // 计时交互
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            $.get(seckill.URL.now(), {}, function (result) {
                if(result && result['success']) {
                    var nowTime = result['data'];
                    // 时间判断,计时交互
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:'+result);
                }
            });
        }
    }
}