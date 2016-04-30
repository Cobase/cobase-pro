import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';

import SidebarHeader from './SidebarHeader';
import ListItem from './SidebarListItem';
import Title from './SidebarSectionTitle';
import Button from './SidebarButton';

export default class Sidebar extends Component {
  constructor(props) {
    super(props);

    this.handleLogout = this.handleLogout.bind(this);
  }

  handleLogout(e) {
    e.preventDefault();

    this.props.onLogout();
  }

  render() {
    const { groups } = this.props;

    return (
      <div className="sidebar">
        <SidebarHeader />

        <nav className="sidebar-navigation">
          <Title>Pages</Title>
          <ListItem title="Dashboard" to="/dashboard" icon="fa-list" />

          <Title>
            Groups
            <Button to="/groups/add" icon="fa-plus" />
          </Title>

          {groups.map(group =>
            <ListItem
              key={group.id}
              title={group.title}
              to={`/group/${group.id}`}
              icon="fa-file-text-o"
            />
          )}

          <Title>User Control</Title>
          <ListItem title="Settings" to="settings" icon="fa-cogs" />
          <ListItem title="Profile" to="profile" icon="fa-user" />

          <a
            className="sidebar-navigation-item"
            onClick={this.handleLogout}
          >
              <i className="fa fa-sign-out fa-fw"></i> Logout
          </a>
        </nav>
      </div>
    );
  }
}

Sidebar.propTypes = {
  onLogout: PropTypes.func.isRequired,
  groups: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      title: PropTypes.string.isRequired,
    })
  ),
};
