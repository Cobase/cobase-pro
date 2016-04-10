import React, { PropTypes } from 'react';

const SidebarSectionTitle = ({ children }) => (
  <h3 className="sidebar-navigation-title">{children}</h3>
);

SidebarSectionTitle.propTypes = {
  children: PropTypes.node.isRequired,
};

export default SidebarSectionTitle;
