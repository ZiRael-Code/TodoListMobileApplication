package com.example.mytodolist.MyModel.DashboardResponse;

public class AppPackage {
    private Dashboard dashboard;
    private FindTaskPackage findTaskPackage;

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public FindTaskPackage getFindTaskPackage() {
        return findTaskPackage;
    }

    public void setFindTaskPackage(FindTaskPackage findTaskPackage) {
        this.findTaskPackage = findTaskPackage;
    }
}
