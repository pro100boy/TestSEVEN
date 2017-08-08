var datatableApiUsers;

$(function () {
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
                "data": "roles"
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
            // sort by last name then by first name
            {"orderData": [0, 1], "targets": 0}
            , {"orderable": false, "targets": [2, 3, 4, 5, 6, 7]}
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
    // remove-all-options-except-first-option
    // $('select').children('option:not(:first)').remove();

    // https://stackoverflow.com/a/47829/7203956
    elem.find('option')
        .remove()
        .end()//;
        .append('<option value="" selected="selected">Select company:</option>');

    // all companies. When Admin creates user and links him with company.
    $.get('companies/', function (data) {
        $.each(data, function (key, value) {
            //$('#mySelect').append( new Option(value,key) );
            //elem.append(new Option(value.name, value.id));

            elem.append($('<option></option>').val(value.id).html(value.name));
        });

        // select user's company when admin edits user
        if (cmpID !== undefined)
            elem.val(cmpID);
    });
}
