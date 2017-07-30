var datatableApiUsers;

$(function () {
    // на будущее
    /*$("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace('international?lang=' + selectedOption);
        }
    });*/
    var ajaxUrl = 'users/';
    var successmsg = 'User successfully saved';

    datatableApiUsers = $('#userTable').DataTable(extendsOpts(ajaxUrl, {
        "columns": [
            {
                "data": "lastname"
            },
            {
                "data": "name"
            },
            {
                "data": "email",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<a href="mailto:' + data + '">' + data + '</a>';
                    }
                    return data;
                }
            },
            {
                "data": "phone"
            },
            {
                "data": "company.name"
            },
            {
                "data": "roles[].role"
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
            {"orderable": false, "targets": [2, 3, 4, 5, 6, 7]}
            , {"width": "15%", "targets": [0, 1, 2]}
            , {"width": "5%", "targets": [6, 7]}
        ]
    }));

    frmDetailsArr[0].validator().on('submit', function (e) {
        e.preventDefault();
        save(frmDetailsArr[0], modalFormArr[0], successmsg, datatableApiUsers);
    });
});

function getCompanies(elem) {
    // https://stackoverflow.com/a/47829/7203956
    elem.find('option')
        .remove()
        .end()//;
        .append('<option value="" selected="selected">Select company:</option>');
    //.val('Select company:');

/*    //datatableApiCmp.rows().every( function ( rowIdx, tableLoop, rowLoop ) { // не выбирается последняя строка ((
    var table = document.getElementById('companyTable');
    // начинаем с 1, т.к. 0 = заголовок таблицы
    for (var i = 1; i < table.rows.length; i++) {
        var val = table.rows[i].cells[0].innerText;
        var id = $(table.rows[i].cells[3].innerHTML).attr("id").replace("rowid", "");
        elem.append($('<option></option>').val(id).html(val));
    }*/

    $.get('/companies', function (data) {
        $.each(data, function (key, value) {
            //$('#mySelect').append( new Option(value,key) );
            //elem.append(new Option(value.name, value.id));

            elem.append($('<option></option>').val(value.id).html(value.name));
        });
    });
}
