$("#deletes-btn").click(function(){	
	var items = [];
    // thẻ input class="select-item"
    $("input.select-item:checked:checked").each(function (index, item) {
    	items[index] = item.value;
    });
    if (items.length == 0) {
    	alert("Bạn chưa chọn gì để xóa cả");
    	return false;
    } else {		
    	return confirm('Bạn có muốn xóa những lịch sử dò số đã chọn?');
    }		
});