var datatableApiReport;

$(function () {
    var ajaxUrl = 'reports/';
    var successmsg = 'Report successfully saved';

    datatableApiReport = $('#reportTable').DataTable(extendsOpts(ajaxUrl, {
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "date",
                "render": function (date, type, row) {
                    if (type == 'display') {
                        return formatDate(date);
                    }
                    return date;
                }
            },
            {
                "data": "data",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        //return '<a href="mailto:' + data + '">' + data + '</a>';
                        return data.substring(0, 30);
                    }
                    return data;
                }
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "columnDefs": [
            {"orderable": false, "targets": [2, 3, 4]}
            , {"width": "10%", "targets": 0}
            , {"width": "15%", "targets": 1}
            , {"width": "30%", "targets": 2}
            , {"width": "5%", "targets": [3,4]}
        ]
    }));

    frmDetailsArr[2].validator().on('submit', function (e) {
        e.preventDefault();
        save(frmDetailsArr[2], modalFormArr[2], successmsg, datatableApiReport);
    });
});

function getCompanies(elem) {
    // https://stackoverflow.com/a/47829/7203956
    elem.find('option')
        .remove()
        .end()//;
        .append('<option value="" selected="selected">Select company:</option>');
    //.val('Select company:');

    //datatableApiCmp.rows().every( function ( rowIdx, tableLoop, rowLoop ) { // не выбирается последняя строка ((
    var table = document.getElementById('companyTable');
    // начинаем с 1, т.к. 0 = заголовок таблицы
    for (var i = 1; i < table.rows.length; i++) {
        var val = table.rows[i].cells[0].innerText;
        var id = $(table.rows[i].cells[3].innerHTML).attr("id").replace("rowid", "");
        elem.append($('<option></option>').val(id).html(val));
    }

    /*    $.get('/companies', function (data) {
     $.each(data, function (key, value) {
     //$('#mySelect').append( new Option(value,key) );
     //elem.append(new Option(value.name, value.id));

     elem.append( $('<option></option>').val(value.id).html(value.name));
     });
     });*/
}

