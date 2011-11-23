$(function() {
    $.localise('ui-multiselect', {language: 'fr', path: '/public/js/locale/'});
    $(".multiselect").multiselect({
        sortable: false
    });
});