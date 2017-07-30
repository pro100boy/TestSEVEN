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
                        return data.substring(0, 60);
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
            , {"width": "20%", "targets": 0}
            , {"width": "15%", "targets": 1}
            , {"width": "5%", "targets": [3, 4]}
        ]
    }));

    frmDetailsArr[2].validator().on('submit', function (e) {
        e.preventDefault();
        save(frmDetailsArr[2], modalFormArr[2], successmsg, datatableApiReport);
    });

    // TODO $.datetimepicker.setLocale('ru');
    //$.datetimepicker.setLocale('ru');

    var dateTime = $('#date');
    dateTime.datetimepicker({
        format: 'Y-m-d H:i',
        //mask: true,
        setDate: new Date()
    });
});

