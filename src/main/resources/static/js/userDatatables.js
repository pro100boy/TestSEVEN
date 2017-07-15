var datatableApiUsers;

$(function () {
    var ajaxUrl = 'users/';
    var successmsg = 'User successfully saved';
    var errormsg = 'An user with such email is already registered in the app!!';

    datatableApiUsers = $('#userTable').DataTable({
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
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "autoWidth": false,
        "paging": true,
        "info": true,
        "columnDefs": [
            {"orderable": false, "targets": [2, 3, 4, 5, 6, 7]}
            , {"width": "15%", "targets": [0, 1, 2]}
            , {"width": "5%", "targets": [6, 7]}
        ]
    });

    frmDetailsArr[0].validator().on('submit', function (e) {
        e.preventDefault();
        save(frmDetailsArr[0], modalFormArr[0], successmsg, errormsg, datatableApiUsers);
    });
});

function getCompanies(elem) {
    $.get('/companies', function (data) {
        $.each(data, function (key, value) {
            //$('#mySelect').append( new Option(value,key) );
            //elem.append(new Option(value.name, value.id));

            elem.append( $('<option></option>').val(value.id).html(value.name));
        });
    });
}