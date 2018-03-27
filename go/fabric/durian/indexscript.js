var url_host = "http://localhost:5000/";
var ccId;
var durians;
var user_name;

$(document).ready(function(){
    console.log('document load');
    var ids = ["test_user0", "test_user1", "test_user2", "test_user3", "test_user4", "test_user5", "test_user6", "test_user7", "test_user8", "test_user9"];
    var pws = ["MS9qrN8hFjlE", "jGlNl6ImkuDo", "zMflqOKezFiA", "vWdLCE00vJy0", "4nXSrfoYGFCP", "yg5DVhm0er1z", "b7pmSxzKNFiw", "YsWZD4qQmYxo", "W8G0usrU7jRk", "H80SiB5ODKKQ"];
    var i;
    for(i=0; i<ids.length; i++) {
        $('#ids').append('<option value="' + ids[i] + '">' + ids[i] + '</option>');
        $('#pws').append('<option value="' + pws[i] + '">' + pws[i] + '</option>');
    }
    $('#ids').change(function(){
        var idsIndex = $('#ids option').index($('#ids option:selected'));
        var pwsIndex = $('#pws option').index($('#pws option:selected'));
        console.log(idsIndex + " , " + pwsIndex);
        $('#pws option:eq(' + idsIndex + ')').attr('selected', 'selected');
        $('#pws option:eq(' + pwsIndex + ')').removeAttr('selected');
    });

    //file read
    var fileInput = $("#fileInput").get(0);
    fileInput.addEventListener("change",
        //change eventListener start
        function(e) {
            fileContent = "";
            var filee = fileInput.files[0];
            var textType = /text.*/;
            if(filee.type.match(textType)) {
                var reader = new FileReader();
                reader.onload = function(e) { $("#data").html(reader.result); }
                reader.readAsText(filee, "UTF-8");
            } else {
                $("#data").html("File not supported");
            }
        }
        //change eventListener end
    );

    //file clear
    $('#fileClear').click(function(){
        $('#fileInput').val('');
        $("#data").html('');
    });

    $("#btnProcess").click(function() {
        console.log("ENTER : " + this.id);
        var pattForDurians = /[\d]*-[\d]*/g;
        var durians = $("#data").html().match(pattForDurians);
        console.dir(durians);
        for(i=0; i<durians.length; i++) {
            var durianNo = durians[i].substring(0, durians[i].indexOf('-'));
            var durianStatus = durians[i].substring(durians[i].indexOf('-')+1);
            console.log(durianNo + " " + durianStatus);
            var JSONdata = createJSONdataForFabric("invoke", "addDurian", [durianNo, durianStatus], 3);
            executeJsonRpc(url_host + "chaincode", JSONdata,
               function success(data) {
                   console.log(JSONdata.method + " " + JSONdata.params.ctorMsg.function + " success!");
               },
               function error(data) {
                   console.log(JSONdata.method + " " + JSONdata.params.ctorMsg.function + " error");
               }
            );
        }
       console.log("LEAVE : " + this.id);
    });
});//END OF DOCUMENT READY

function login() {
    user_name = $("#ids").val();
    var password = $("#pws").val();
    var JSONdata = {
        "enrollId": user_name,
        "enrollSecret": password
    };
    executeJsonRpc(url_host + "registrar", JSONdata,
        function success(data) {
        // 로그인 성공
            console.log("login success!");
        },
        function error(data) {
        // 로그인 실패
            console.log("login error");
        }
    );
}

function deploy() {
    var JSONdata = createJSONdataForFabric("deploy", "init", [], 1);
    executeJsonRpc(url_host + "chaincode", JSONdata,
        function success(data) {
            ccId = data.result.message;
            console.log(JSONdata.method + " " + JSONdata.params.ctorMsg.function + " success! : ccId : " + ccId);
        },
        function error(data) {
            console.log(JSONdata.method + " " + JSONdata.params.ctorMsg.function + " error");
        }
    );
}

function refresh() {
    if (durians != null) {
        var table = document.getElementById('list');
        for (var i = durians.length; i > 0; i--) {
            table.deleteRow(i);
        }
    }
    getDurians();
}

// 카운터 취득
function getDurians() {
    var JSONdata = createJSONdataForFabric("query", "getDurians", [], 5);
    executeJsonRpc(url_host + "chaincode", JSONdata,
        function success(data) {
            console.dir(data);
            durians = JSON.parse(data.result.message);
            if(durians != undefined && durians != null && durians.length > 0) {
                var table = document.getElementById('list');
                for (var i = 0; i < durians.length; i++) {
                    // 카운터 정보 취득
                    var durian = durians[i];
                    console.log("[" + i + "]", durian);

                    var row = table.insertRow();
                    var td = row.insertCell(0);
                    td.innerHTML = durian.no;
                    td = row.insertCell(1);
                    td.innerHTML = durian.status;
                }
            }
        },
        function error(data) {
            console.log("refresh error");
        }
    );
}

function createJSONdataForFabric(method, functionName, args, id) {
    var JSONdata = {
        jsonrpc: "2.0",
        method: method,
        params: {
            type: 1,
            ctorMsg: {
                function: functionName,
                args: args
            },
            secureContext: user_name,
        },
        id: id
    };

    if (functionName == "init") {
        JSONdata.params["chaincodeID"] = {
            path: "github.com/hyperledger/fabric/examples/chaincode/go/chaincode_durian"
        };
    } else {
        JSONdata.params["chaincodeID"] = {
            name: ccId
        };
    }
    return JSONdata;
}

function executeJsonRpc(url_exec, JSONdata, success, error) {
    $.ajax({
        type: 'post',
        url: url_exec,
        data: JSON.stringify(JSONdata),
        contentType: 'application/JSON;',
        dataType: 'JSON',
        scriptCharset: 'utf-8',
        success: function(data) {
            success(data);
        },
        error: function(data) {
            error(data);
        }
    });
}
