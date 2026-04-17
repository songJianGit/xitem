(function discussDock() {
    var $body = $('body');
    var $dock = $('#discussDock');
    var $collapsed = $('#discussDockCollapsed');
    var $expanded = $('#discussDockExpanded');
    var $editBanner = $('#discussEditBanner');
    var $banner = $('#discussReplyBanner');
    var $authorDisplay = $('#discussReplyAuthorDisplay');
    var articleId = String($dock.data('aid') || '').trim();
    var replyAuthor = '';
    var replyAuthorId = '';
    var editCommentId = '';
    var editCommentType = '';
    var editCommentParentId = '';
    var DOCK_PAD_GAP = 24;

    function updateDiscussDockPad() {
        if (!$dock.length) {
            return;
        }
        var el = $dock[0];
        var h = el.getBoundingClientRect().height;
        var pad = Math.ceil(h) + DOCK_PAD_GAP;
        document.documentElement.style.setProperty('--discuss-dock-pad', pad + 'px');
    }

    function updateDiscussDockPadSoon() {
        window.requestAnimationFrame(function () {
            window.requestAnimationFrame(updateDiscussDockPad);
        });
    }

    function setDockOpen(open) {
        if (open) {
            $expanded.addClass('is-visible');
            $collapsed.hide();
            $body.addClass('discuss-dock-open');
        } else {
            $expanded.removeClass('is-visible');
            $collapsed.show();
            $body.removeClass('discuss-dock-open');
        }
        updateDiscussDockPadSoon();
    }

    function setReplyTarget(name, authorId) {
        replyAuthor = (name || '').trim();
        replyAuthorId = (authorId || '').trim();
        if (replyAuthor) {
            $authorDisplay.text(replyAuthor);
            $banner.addClass('is-visible');
        } else {
            $banner.removeClass('is-visible');
            $authorDisplay.text('');
        }
        updateDiscussDockPadSoon();
    }

    function setEditTarget(commentId, commentType, parentId) {
        editCommentId = (commentId || '').trim();
        editCommentType = (commentType || '').trim();
        editCommentParentId = (parentId || '').trim();
        if (editCommentId) {
            $editBanner.addClass('is-visible');
            $('#discussSendBtn').html('<i class="mdi mdi-check mr-1"></i>保存修改');
        } else {
            $editBanner.removeClass('is-visible');
            $('#discussSendBtn').html('<i class="mdi mdi-send mr-1"></i>发表');
        }
        updateDiscussDockPadSoon();
    }

    function getDiscussContent() {
        var editor = tinymce.get('discussInput');
        if (editor) {
            return (editor.getContent({format: 'raw'}) || '').trim();
        }
        return ($('#discussInput').val() || '').trim();
    }

    function setDiscussContent(content) {
        var text = content || '';
        var editor = tinymce.get('discussInput');
        if (editor) {
            editor.setContent(text);
        }
        $('#discussInput').val(text);
    }

    if ($dock.length) {
        $(window).on('resize.discussDock', updateDiscussDockPadSoon);
        if (window.ResizeObserver) {
            var ro = new ResizeObserver(function () {
                updateDiscussDockPadSoon();
            });
            ro.observe($dock[0]);
        }
        updateDiscussDockPadSoon();
    }

    function openForMainComment() {
        setReplyTarget('','');
        setEditTarget('', '', '');
        setDockOpen(true);
        $('#discussInput').trigger('focus');
    }

    function openForReply(author, authorId) {
        setEditTarget('', '', '');
        setReplyTarget(author, authorId);
        setDockOpen(true);
        $('#discussInput').trigger('focus');
    }

    function openForEdit(commentId, commentType, parentId, content) {
        setReplyTarget('', '');
        setEditTarget(commentId, commentType, parentId);
        setDockOpen(true);
        setDiscussContent(content);
        $('#discussInput').trigger('focus');
    }

    function collapseDock() {
        setDockOpen(false);
        setReplyTarget('','');
        setEditTarget('', '', '');
        setDiscussContent('');
    }

    $collapsed.on('click', function () {
        openForMainComment();
    });
    $collapsed.on('keydown', function (e) {
        if (e.key === 'Enter' || e.key === ' ') {
            e.preventDefault();
            openForMainComment();
        }
    });

    $(document).on('click', '.discuss-reply-btn', function (e) {
        e.preventDefault();
        var author = $(this).data('reply-author');
        var authorId = $(this).data('reply-author-id');
        if (author) {
            openForReply(String(author), String(authorId));
        }
    });

    $(document).on('click', '.discuss-edit-btn', function (e) {
        e.preventDefault();
        var commentId = $(this).data('comment-id');
        var commentType = $(this).data('comment-type');
        var parentId = $(this).data('comment-parent-id');
        var content = $(this).data('comment-content');
        if (!commentId) {
            layer.msg('评论ID缺失，无法编辑');
            return;
        }
        openForEdit(String(commentId), String(commentType || ''), String(parentId || ''), String(content || ''));
    });

    $(document).on('click', '.discuss-del-btn', function (e) {
        e.preventDefault();
        var commentId = $(this).data('comment-id');
        if (!commentId) {
            layer.msg('评论ID缺失，无法删除');
            return;
        }
        layer.confirm('确认删除该评论吗？', {icon: 3, title: '提示'}, function (index) {
            $.ajax({
                url: contextPath + "/admin/comments/delById",
                data: {
                    id: commentId
                },
                success: function (d) {
                    layer.msg(d.msg);
                    setTimeout(function () {
                        window.location.reload();
                    }, 500);
                }
            });
            layer.close(index);
        });
    });

    $('#discussCancelReplyBtn').on('click', function () {
        setReplyTarget('','');
    });
    $('#discussCancelEditBtn').on('click', function () {
        setEditTarget('', '', '');
        setDiscussContent('');
    });

    $('#discussCollapseBtn').on('click', function () {
        collapseDock();
    });

    $('#discussClearBtn').on('click', function () {
        setDiscussContent('');
    });

    $('#discussSendBtn').on('click', function () {
        let t = getDiscussContent();
        if (!t) {
            layer.msg('请先输入评论内容');
            return;
        }
        let dataJson = {};
        if (editCommentId) {
            dataJson = {
                id: editCommentId,
                aid: articleId,
                content: t,
                type: Number(editCommentType || 1)
            };
            if (Number(editCommentType || 1) === 2) {
                dataJson.comId = editCommentParentId;
            }
        } else if (replyAuthor) {
            // layer.msg('界面预览：将作为对「' + replyAuthor + '」的回复提交（接口待对接）');
            dataJson = {
                aid: articleId,
                content: t,
                type: 2,
                comId: replyAuthorId
            };
        } else {
            // layer.msg('界面预览：将作为主评论提交（接口待对接）');
            dataJson = {
                aid: articleId,
                content: t,
                type: 1
            };
        }
        $.ajax({
            url: contextPath + "/admin/comments/save",
            type: "post",
            data: dataJson,
            success: function (d) {
                layer.msg(d.msg);
                setTimeout(function (){
                    window.location.reload();
                }, 500);
            }
        });
    });
})();