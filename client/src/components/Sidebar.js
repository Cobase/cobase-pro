import React, { Component, PropTypes } from 'react';

class Sidebar extends Component {
  render() {
    return (
      <div className="sidebar">
        <header className="sidebar-header">
          <h1 className="sidebar-header-title">
            Cobase<span className="sidebar-header-title__light">PRO</span>
          </h1>
        </header>

        <nav className="sidebar-navigation">
          <h3 className="sidebar-navigation-title">Pages</h3>
          <a className="sidebar-navigation-item"><i className="fa fa-list fa-fw"></i> Dashboard</a>

          <h3 className="sidebar-navigation-title">Groups</h3>
          <a className="sidebar-navigation-item"><i className="fa fa-plus fa-fw"></i> Create new group</a>
          <a className="sidebar-navigation-item"><i className="fa fa-file-text-o fa-fw"></i> Duuniasiat</a>
          <a className="sidebar-navigation-item"><i className="fa fa-file-text-o fa-fw"></i> Kissavideot</a>

          <h3 className="sidebar-navigation-title">User Control</h3>
          <a className="sidebar-navigation-item"><i className="fa fa-cogs fa-fw"></i> Settings</a>
          <a className="sidebar-navigation-item"><i className="fa fa-user fa-fw"></i> Profile</a>
          <a className="sidebar-navigation-item" onClick={this.handleLogout.bind(this)}><i className="fa fa-sign-out fa-fw"></i> Logout</a>
        </nav>
      </div>
    );
  }

  handleLogout(e) {
    e.preventDefault();

    this.props.onLogout();
  }
}

Sidebar.propTypes = {
  onLogout: PropTypes.func.isRequired
};

export default Sidebar;
