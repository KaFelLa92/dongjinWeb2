import { Link } from "react-router-dom";
import Box from '@mui/joy/Box';
import Chip from '@mui/joy/Chip';
import Tabs from '@mui/joy/Tabs';
import TabList from '@mui/joy/TabList';
import Tab, { tabClasses } from '@mui/joy/Tab';
import TabPanel from '@mui/joy/TabPanel';
import { useState } from "react";

export default function Footer(props) {

    const [index, setIndex] = useState(0);

    return (<>
        <Box sx={{ position:"fixed", bottom:"0" }}>
            <Tabs
                aria-label="Pipeline"
                value={index}
                onChange={(event, value) => setIndex(value)}
            >
                <TabList
                    sx={{
                        pt: 1,
                        justifyContent: 'center',
                        [`&& .${tabClasses.root}`]: {
                            flex: 'initial',
                            bgcolor: 'transparent',
                            '&:hover': {
                                bgcolor: 'transparent',
                            },
                            [`&.${tabClasses.selected}`]: {
                                color: 'primary.plainColor',
                                '&::after': {
                                    height: 2,
                                    borderTopLeftRadius: 3,
                                    borderTopRightRadius: 3,
                                    bgcolor: 'primary.500',
                                },
                            },
                        },
                    }}
                >
                    <Tab indicatorInset>
                        <Link to="/"> 홈 </Link>
                        <Chip
                            size="sm"
                            variant="soft"
                            color={index === 0 ? 'primary' : 'neutral'}
                        >
                            
                        </Chip>
                    </Tab>
                    <Tab indicatorInset>
                        <Link to="/menu"> 메뉴 선택 </Link>{' '}
                        <Chip
                            size="sm"
                            variant="soft"
                            color={index === 1 ? 'primary' : 'neutral'}
                        >
                        </Chip>
                    </Tab>
                    <Tab indicatorInset>
                        <Link to="/cart"> 장바구니 </Link>
                        <Chip
                            size="sm"
                            variant="soft"
                            color={index === 2 ? 'primary' : 'neutral'}
                        >
                        </Chip>
                    </Tab>
                </TabList>
                <Box
                    sx={(theme) => ({
                        '--bg': theme.vars.palette.background.surface,
                        background: 'var(--bg)',
                        boxShadow: '0 0 0 100vmax var(--bg)',
                        clipPath: 'inset(0 -100vmax)',
                    })}
                >
                    <TabPanel value={0}>Deals</TabPanel>
                    <TabPanel value={1}>Library</TabPanel>
                    <TabPanel value={2}>Products</TabPanel>
                </Box>
            </Tabs>
        </Box>


    </>)
}