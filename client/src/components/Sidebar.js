import React from 'react';

export default () => (
  <div className="sidebar">
    <header className="sidebar-header">
      <h1 className="sidebar-header-title">
        Cobase<span className="sidebar-header-title__light">PRO</span>
      </h1>
    </header>

    <nav className="sidebar-navigation">
      <h3 className="sidebar-navigation-title">Groups</h3>
      <a className="sidebar-navigation-item">Dashboard</a>
      <a className="sidebar-navigation-item">Duuniasiat</a>
      <a className="sidebar-navigation-item">Kissavideot</a>

      <h3 className="sidebar-navigation-title">User Control</h3>
      <a className="sidebar-navigation-item">Settings</a>
      <a className="sidebar-navigation-item">Profile</a>
      <a className="sidebar-navigation-item">Logout</a>
    </nav>
  </div>
);
