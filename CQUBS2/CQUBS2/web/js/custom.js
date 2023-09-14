function deleteCatConfirm()
{
    var confirmText = "Deleting this category will also permanently delete all associated services and their registrations.\nAre you sure you want to delete this category?";
                
    if(confirm(confirmText) == true)
    {
        document.getElementById("hiddenForm:hiddenBtn").click();
    }
}

function deleteServConfirm()
{
    var confirmText = "Deleting the service at this location will also permanently delete all associated registrations.\nAre you sure you want to delete this service?";
    
    if(confirm(confirmText) == true)
    {
        document.getElementById("hiddenForm:hiddenBtn").click();
    }
}


