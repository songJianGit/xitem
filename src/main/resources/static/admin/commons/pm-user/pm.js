(function () {
    var $list = $('#pmMemberList');
    var $stat = $('#pmStat');
    var $search = $('#pmSearch');

    function pmSyncRow($row) {
        var on = $row.find('.pm-in-project-cb').prop('checked');
        $row.toggleClass('is-in-project', !!on);
        $row.find('.pm-mode-group').toggleClass('is-disabled', !on);
    }

    function pmUpdateStat() {
        var total = $list.children('li.pm-item').length;
        var joined = $list.find('.pm-in-project-cb:checked').length;
        $stat.html('已加入 <strong class="text-dark">' + joined + '</strong> / 共 <strong class="text-dark">' + total + '</strong> 人');
    }

    function pmApplySearch() {
        var q = ($search.val() || '').trim().toLowerCase();
        $list.children('li.pm-item').each(function () {
            var name = ($(this).attr('data-pm-name') || '').toLowerCase();
            var show = !q || name.indexOf(q) !== -1;
            $(this).toggleClass('pm-row-hidden', !show);
        });
    }

    $list.find('li.pm-item').each(function () {
        pmSyncRow($(this));
    });

    $list.on('change', '.pm-in-project-cb', function () {
        pmSyncRow($(this).closest('li.pm-item'));
        pmUpdateStat();
    });

    $list.on('click', '.pm-mode-btn', function () {
        var $row = $(this).closest('li.pm-item');
        if (!$row.hasClass('is-in-project')) {
            return;
        }
        var $g = $(this).closest('.pm-mode-group');
        $g.find('.pm-mode-btn').removeClass('active');
        $(this).addClass('active');
    });

    $search.on('input', function () {
        pmApplySearch();
    });

    pmUpdateStat();
})();