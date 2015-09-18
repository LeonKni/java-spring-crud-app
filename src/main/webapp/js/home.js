/**
 * @author Leon K
 */
(function () {
    'use strict';

    angular
        .module('UnityApp')
        .controller('HomeCtrl', HomeCtrl);

    /**
     * Home Controller
     */
    HomeCtrl.$inject = ['$http'];
    function HomeCtrl($http) {
        var model = this;
        model.title = 'Settings';
        model.addRowSettings = addRowSettings;
        model.deleteRow = deleteRow;
        model.filterSettings = getSettingsByGroup;
        model.save = save;
        model.settings = getSettings();
        model.groups = getGroups();
        model.getSubGroupOptions = getSubGroupOptions;
        model.resetSubGroups = resetSubGroups;


        //-----------------------------------------Helper methods---------------------------------------------//
        /**
         * Add a new row to Settings table.
         */
        function addRowSettings() {
            var select = angular.element('<select name="groupName" class="form-control" ' +
                'data-ng-model="selectedGroup"' +
                ' data-ng-options="group.name for group in homeCtrl.groups" ' +
                'data-ng-change="homeCtrl.resetSubGroups(selectedGroup.name, setting.settingKey)">');
            var defaultOption = document.createElement('option');
            var option;
            var groups = model.groups;
            select.className = 'form-control';
            select.name = 'selectedGroupName';
            defaultOption.value = '';
            defaultOption.innerHTML = '-Select-';
            select.append(defaultOption);
            for (var i = 0; i < groups.length; i++) {
                option = document.createElement('option');
                option.value = groups[i].name;
                option.innerHTML = groups[i].name;
                select.append(option);
            }

            $('#settingsTable').find('tbody')
                .append('<tr data-ng-controller="HomeCtrl">' +
                '<td><input name="checkbox" type="checkbox"/></td>' +
                '<td><input class="form-control" name="settingKey" type="text"/></td>' +
                '<td><input class="form-control" name="settingValue" type="text"/></td>' +
                '<td>' + select[0].outerHTML + '</td>' +
                '<td><select class="form-control" name="selectedSubGroupName">' +
                '<option value="">-Select-</option>' +
                '</select></td>' +
                '</tr>');
        }

        /**
         * Delete a row.
         */
        function deleteRow() {
            var keyList = [];
            var rowIndex = '';
            var tableIdSelector = '#settingsTable';
            //Create list of selected rows
            $(tableIdSelector).find('tbody tr input:checked').each(function () {
                rowIndex = $(this).closest('tr')[0].rowIndex - 1;
                var key = $(this).closest('tr').find('input[name="settingKey"]')[0].value;
                if (key != '') {
                    keyList.push(key);
                }
                //Remove the physical table row
                $(tableIdSelector).find('tbody tr')[rowIndex].remove();
                console.log(keyList);
            });
            //API DELETE
            var request = {
                method: 'DELETE',
                url: '/settings',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: keyList
            };
            $http(request).then(function (data) {
                //Success message
            });
        }

        /**
         * Get all groups.
         */
        function getGroups() {
            return $http.get("/settings/groups")
                .success(function (data) {
                    model.groups = data;
                })
        }

        /**
         * Find group by name.
         */
        function getSettingsByGroup(groupName) {
            return $http.get("/settings/" + groupName);
        }

        /**
         * Get sub-groups based on selected group.
         * @param groupName
         * @returns array of sub-groups
         */
        function getSubGroupOptions(groupName) {
            var groups = model.groups;
            for (var i = 0; i < groups.length; i++) {
                if (groups[i].name === groupName) {
                    return groups[i].subGroups;
                }
            }
        }

        /**
         * Reset dropdown options.
         * @param groupName
         * @param settingKey
         */
        function resetSubGroups(groupName, settingKey) {
            var select = $('#settingsTable select[name="subGroupName"]')
                [$('#settingsTable input[value=' + settingKey + ']')[0].parentNode.parentNode.rowIndex - 1];
            var subGroupNames = [];
            while (select.firstChild) {
                select.removeChild(select.firstChild);
            }
            for (var i = 0; i < model.groups.length; i++) {
                if (model.groups[i].name === groupName) {
                    for (var j = 0; j < model.groups[i].subGroups.length; j++) {
                        subGroupNames.push(model.groups[i].subGroups[j].name);
                    }
                }
            }
            var defaultOption = document.createElement('option');
            var option;
            defaultOption.value = '';
            defaultOption.innerHTML = '-Select-';
            select.appendChild(defaultOption);
            for (var k = 0; k < subGroupNames.length; k++) {
                option = document.createElement('option');
                option.value = subGroupNames[k];
                option.innerHTML = subGroupNames[k];
                select.appendChild(option);
            }
        }

        /**
         * Get all settings.
         */
        function getSettings() {
            return $http.get("/settings")
                .success(function (data) {
                    model.settings = data;
                    //Pre-select dropdown options based on setting
                    setTimeout(function () {
                        for (var i = 0; i < data.length - 1; i++) {
                            //pre-select group
                            for (var j = 0; j < $('#settingsTable select[name="groupName"]')[i].children.length; j++) {
                                if (data[i].groupName != null &&
                                    $('#settingsTable select[name="groupName"]')[i].children[j].label === data[i].groupName) {
                                    $('#settingsTable select[name="groupName"]')[i].selectedIndex
                                        = $('#settingsTable select[name="groupName"]')[i].children[j].index;
                                }
                            }
                            //pre-select sub-group
                            for (var k = 0; k < $('#settingsTable select[name="subGroupName"]')[i].children.length; k++) {
                                if (data[i].subGroupName != null &&
                                    $('#settingsTable select[name="subGroupName"]')[i].children[k].label === data[i].subGroupName) {
                                    $('#settingsTable select[name="subGroupName"]')[i].selectedIndex
                                        = $('#settingsTable select[name="subGroupName"]')[i].children[k].index;
                                }
                            }
                        }
                    }, 100);
                });
        }

        /**
         * Save changes.
         */
        function save() {
            var closeBtn = document.createElement('a');
            var data = [];
            var tableIdSelector = '#settingsTable';
            closeBtn.setAttribute('class', 'close');
            closeBtn.setAttribute('aria-label', 'close');
            closeBtn.innerHTML = '&times';
            $(tableIdSelector).find('tbody tr').each(function () {
                //only save newly added rows
                data.push(
                    {
                        settingKey: $(this).find('input[name="settingKey"]')[0].value,
                        settingValue: $(this).find('input[name="settingValue"]')[0].value,
                        groupName: $(this).find('select[name="groupName"]')[0].selectedOptions[0].textContent,
                        subGroupName: $(this).find('select[name="subGroupName"]')[0].selectedOptions[0].textContent
                    }
                )
            });
            //API
            $http.put("/settings", data)
                .then(function () {
                    //success message
                    var alert = $('#alert-settings-success');
                    alert[0].textContent = 'Successfully saved changes.';
                    alert[0].appendChild(closeBtn);
                    alert.show().delay(3000).fadeOut();
                })
                .then(function () {
                    //error message
                    var alert = $('#alert-settings-error');
                    alert[0].textContent = 'ERROR - ' + error;
                    alert[0].appendChild(closeBtn);
                    alert.show();
                });
            //Disable input field on save
            $(tableIdSelector).find('input[name="settingKey"]').each(function () {
                if (!this.disabled) {
                    this.disabled = true;
                    console.log('disabled');
                }
            })
        }
    }
})();
