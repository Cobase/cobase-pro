import React from 'react';
import { Link } from 'react-router';

const SidebarSectionButton = ({to, icon}) => {
  const iconClasses = `fa ${icon} fa-fw`;

  return (
    <Link
      to={to}
      className="sidebar-navigation-button"
    >
      <i className={iconClasses} />
    </Link>
  );
};

export default SidebarSectionButton;
